#include <iostream>
#include "trialfuncs.hpp"
#include "compfuncs.hpp"
#include <string>
#include <thread>
#include <condition_variable>
#include <mutex>
#include <optional>
#include <functional>
#include <chrono>
#include <windows.h>
#include <conio.h>
#include <future>


class manager
{
public:
	using functionRes = std::tuple<char, std::optional<int>,decltype(&os::lab1::compfuncs::trial_f<os::lab1::compfuncs::INT_SUM>)>;

	manager()
	{
		isProgramStopped = false;
	}

	void start() {
		hotKeyProcessor = std::thread([&](){this->hotKeyProcessing();});

		while (!isProgramStopped)
		{
			int x = -1;
		
				promptMutex.lock();
				std::cout << "Enter x\n[Enter '-1' if you want to exit...]\n";
				std::cin >> x;
				promptMutex.unlock();
				if (x == -1)
				{
					stop();
					return;
				}
				std::cin.clear();
				std::cin.ignore(std::cin.rdbuf()->in_avail());

			results.push_back(functionRes('F', std::optional<int>{}, os::lab1::compfuncs::trial_f<os::lab1::compfuncs::INT_SUM>));
			results.push_back(functionRes('G', std::optional<int>{}, os::lab1::compfuncs::trial_g<os::lab1::compfuncs::INT_SUM>));
			
			threads.resize(results.size());
			for(int i=0; i< threads.size();i++)
			threads[i] = std::thread([i,x,this] {this->runFunction(std::get<functionsTupleParams::FUNC_FUNCTION>(
				this->results[i]), x, this->results[i]); });
			

			std::unique_lock<std::mutex>lock(mut);
			cv.wait_for(lock,std::chrono::seconds(waitTime),[this]{
				if (this->isProgramStopped)
					return true;
				bool stopWaiting = true;
				for (int i = 0; i < this->results.size(); i++)
					stopWaiting = stopWaiting && std::get<functionsTupleParams::FUNC_RES>(this->results[i]).has_value();
				return stopWaiting;
				});

			{
				std::unique_lock<std::mutex>lock(promptMutex);
				if (isProgramStopped)
				{
					stop();
					return;
				}
			}

			for(int i=0; i<results.size();i++)
			if (std::get<functionsTupleParams::FUNC_RES>(results[i]).has_value())
				std::cout << "Result of "<< std::get<functionsTupleParams::FUNC_LETTER>(results[i]) <<" function: "
				<< std::get<functionsTupleParams::FUNC_RES>(results[i]).value() << std::endl;
			else {
				threads[i].detach();
				stop();
				throw std::runtime_error(std::string(1, std::get<functionsTupleParams::FUNC_LETTER>(results[i]))
					+ " function does not respond. Hard fail.");
			}
			

			for (auto& i : threads)
				i.join();
			threads.clear();
			results.clear();
		}

		hotKeyProcessor.join();

	}

	void stop()
	{
		this->isProgramStopped = true;
		hotKeyProcessor.join();
	}
private:
	void runFunction (
		std::function<typename os::lab1::compfuncs::op_group_traits<os::lab1::compfuncs::INT_SUM>::result_type(int)> func,
		int x, functionRes& funcRes)
		 {
		
		
		while(!this->isProgramStopped){
			auto resVar = func(x);
			switch (resVar.index())
			{
			case(0):
				{
				std::unique_lock<std::mutex>lock(mut);
				std::get<functionsTupleParams::FUNC_RES>(funcRes) = std::nullopt;
				}
				return;
				break;
			case(1):
				std::cout << "Soft fail " << std::get<functionsTupleParams::FUNC_LETTER>(funcRes) <<" function\n";
				break;
			case(2):
				{	
				std::get<functionsTupleParams::FUNC_RES>(funcRes) = std::get<int>(resVar);
				cv.notify_one();
				}
				return;
				break;

			}
		}
		


	}

	void hotKeyProcessing()
	{
		const int hotKeyId = 1;
		RegisterHotKey(NULL, hotKeyId, MOD_CONTROL | MOD_NOREPEAT, 0x43);
		MSG msg;
		while (!this->isProgramStopped) {
			
			

			if (PeekMessageA(&msg, NULL, 0, 0, PM_REMOVE) > 0 && msg.message == WM_HOTKEY && msg.wParam == hotKeyId && promptMutex.try_lock()==true)
			{
				std::cout << "Do you want to continue?\n[Y/N]\n";

				std::atomic_bool isInterrupted = false;
				std::packaged_task<char()> task([&] {
					char answer = ' ';
					std::cout << answer;
					while (!isInterrupted && answer != 'Y' && answer != 'N')
					{
						if (_kbhit()) {
							std::cout << std::string(1, '\b');
							answer = _getch();
							std::cout << answer;
						}
					}
					return answer;
					});

				std::future<char> answer = task.get_future();
				std::thread thr = std::thread(std::move(task));

				auto state = answer.wait_for(std::chrono::seconds(waitTime));
				if (state == std::future_status::ready )
				{
					auto res = answer.get();
					if (res == 'Y') {
						
						std::cout << "\nProgramm has been stopped\n";
						this->isProgramStopped = true;
						this->cv.notify_one();
					}
					else if (res == 'N')
						std::cout << "\nProgramm has been resumed\n";
				}
				else if (state == std::future_status::timeout)
				{
					std::cout << "Request timed out\n";
					isInterrupted = true;

				}


				thr.join();
				promptMutex.unlock();
			}
				


		}


		UnregisterHotKey(NULL, hotKeyId);
	}

	std::atomic_bool isProgramStopped;
	std::mutex mut;
	std::mutex promptMutex;
	std::condition_variable cv;
	std::string input;
	std::vector<std::thread> threads;
	std::thread hotKeyProcessor;
	std::vector<functionRes> results;
	const int waitTime = 5;

	enum functionsTupleParams {FUNC_LETTER, FUNC_RES, FUNC_FUNCTION};
	
};

int main() {
	manager m;
	
	try
	{
		m.start();
	}
	catch (const std::exception&e)
	{
		std::cout << e.what();
	}
	
	return 0;
}



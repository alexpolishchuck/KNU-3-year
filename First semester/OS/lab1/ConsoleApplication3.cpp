#define DOCTEST_CONFIG_IMPLEMENT_WITH_MAIN
#include <iostream>
#include <vector>
#include <array>
#include <mutex>
#include <thread>
#include <future>
#include <numeric>
#include <queue>
#include <map>
#include <stack>
#include "E:/programming/doctest/doctest-master/doctest/doctest.h"
class ThreadPool {
public:
	void Start();
	template <typename T>
	auto QueueJob(T job)->std::future <decltype(job())>;
	void Stop();
	bool busy();
	~ThreadPool();
private:
	void ThreadLoop();

	bool should_terminate = false;           // Tells threads to stop looking for jobs
	std::mutex queue_mutex;                  // Prevents data races to the job queue
	std::condition_variable mutex_condition; // Allows threads to wait on new jobs or termination 
	std::vector<std::thread> threads;

	class func_wrapper
	{
	private:
		struct func_base
		{
			virtual void call() = 0;
			virtual ~func_base() {};
		};

		template <typename T>
		struct func_type : public func_base
		{
			T func;

			func_type(T&& f) :func(std::move(f)) {};
			void call()
			{
				func();
			}
		};

		std::unique_ptr<func_base> fnc;
	public:
		template <typename T>
		func_wrapper(T&& f) :fnc(new func_type<T>(std::move(f))) {};
		func_wrapper() = default;
		func_wrapper& operator=(func_wrapper&& other)
		{
			fnc = std::move(other.fnc);
			return *this;
		}
		func_wrapper(func_wrapper&& other) :fnc(std::move(other.fnc)) {};
		
		//func_wrapper(const func_wrapper&) = delete;
	//	func_wrapper(func_wrapper&) = delete;
		func_wrapper& operator= (const func_wrapper&) = delete;
		void operator()()
		{
			fnc->call();
		}

			
	};

	std::queue<func_wrapper> jobs;

	

};

void ThreadPool::Start()
{
	threads.resize(2);
	for (int i = 0; i < 2; i++)
	{
		
		threads[i] = std::thread([this] {this->ThreadLoop(); });
	}
}
template <typename T>
auto ThreadPool::QueueJob(T job)->std::future <decltype(job())>
{
	std::future<decltype(job())>res;
	
		std::unique_lock<std::mutex>lock(queue_mutex);
		std::packaged_task<decltype(job())()> pack_task(std::move(job));
		res = pack_task.get_future();
		jobs.push(std::move(pack_task));
	
	mutex_condition.notify_one();
	return res;
}
void ThreadPool::Stop()
{
	{
		std::unique_lock<std::mutex>lock(queue_mutex);
		should_terminate = true;
	}
	mutex_condition.notify_all();
	for (auto& i : threads)
		i.join();
	threads.clear();
}
bool ThreadPool::busy()
{
	{
		std::unique_lock<std::mutex>lock(queue_mutex);
		
	}
	return !jobs.empty();
}
ThreadPool::~ThreadPool()
{
	Stop();
}
void ThreadPool::ThreadLoop()
{
	while (true) {
		func_wrapper job;
		{
			std::unique_lock<std::mutex>lock(this->queue_mutex);
			mutex_condition.wait(lock, [this] {
				return !jobs.empty() || should_terminate; });
			if (should_terminate)
			{
				return;
			}

			job = std::move(jobs.front());
			jobs.pop();
		}
		job();
	}
}

class TweetCounts {
public:
	TweetCounts() {

	}

	void recordTweet(std::string tweetName, int time) {
		tweets.insert({ time,tweetName });
	}

	std::vector<int> getTweetCountsPerFrequency(std::string freq, std::string tweetName, int startTime, int endTime) {
		int increment = 59;

		if (freq == "minute")
			increment = 59;
		else if (freq == "hour")
			increment = 3599;
		else if (freq == "day")
			increment = 86399;


		int left = startTime;
		int right = startTime + increment;
		auto i = tweets.begin();
		while (i != tweets.end() && i->first < left)
			i++;
		int counter = 0;
		std::vector<int> res;

		while (left <= endTime)
		{
			while (i != tweets.end() && i->first >= left && i->first <= right) {
				if (i->second == tweetName)
					counter++;
				i++;
			}
			res.push_back(counter);
			counter = 0;
			left = std::min(endTime, left + increment + 1);
			right = std::min(endTime, right + increment + 1);

		}
		return res;
	}

private:
	std::multimap<int, std::string> tweets;
};
double maxProbability(int n, std::vector<std::vector<int>>& edges, std::vector<double>& succProb, int start, int end) {

	std::vector<std::vector<double>> edgs(n, std::vector<double>(n, 0));

	int numberofedges = edges.size();
	for (int i = 0; i < numberofedges; i++) {

		edgs[edges[i][0]][edges[i][1]] = succProb[i];
		edgs[edges[i][1]][edges[i][0]] = succProb[i];
	}

	std::stack<int> nodes;
	std::vector<int>parents(n, -1);
	std::vector<double>weights(n, 0);
	weights[start] = 0;
	parents[start] = start;
	nodes.push(start);

	while (!nodes.empty())
	{
		int node = nodes.top();
		nodes.pop();
		for (int i = 0; i < n; i++)
		{
			if (i == node || parents[node] == i || edgs[node][i] == 0)
				continue;

			if (parents[i] == -1)
			{
				weights[i] = weights[node] + log10(edgs[node][i]);
				nodes.push(i);
				parents[i] = node;
			}
			else if (parents[i] != -1)
			{
				if (weights[i] != -1 && weights[node] + log10(edgs[node][i]) > weights[i]) {
					weights[i] = weights[node] + log10(edgs[node][i]);
					parents[i] = node;
				}
			}
			if (i == n - 1)
				weights[node] = -1;
		}
	}
	int res = 1;
	int i = end;
	while (i != start)
	{
		res *= edgs[parents[i]][i];
		i = parents[i];
	}
	return res;
}



int primsalgo(std::vector<std::vector<int>> edges)
{

	if (edges.empty())
		return {};
	int size = edges.size();
	int res=0;
	std::vector<int> visited(size,0);
	std::vector<int>offspring(size,-1);
	std::multimap<int, std::pair<int,int>> nodes;
	

	visited[0] = 1;
	for (int i = 0; i < size; i++)
	{
		if (edges[0][i] && !visited[i])
			nodes.insert({ edges[0][i], {0,i} });
	}
	
	while (!nodes.empty())
	{
		auto iter = nodes.begin();
		while (iter!= nodes.end() && visited[iter->second.first] && visited[iter->second.second])
			iter = nodes.erase(iter);
		if ( iter == nodes.end())
			break;
		int node;
		if (visited[iter->second.first]) {
			node = iter->second.second;
			
		}
		else {
			node = iter->second.first;
			
		}
		
		
		res += iter->first;
		iter = nodes.erase(iter);
		visited[node] = 1;
		for (int i = 0; i < size;i++)
		{
			if(edges[node][i] && !visited[i])
				nodes.insert({ edges[node][i], {node,i}});
		}
		
		
	}

	int node = 0;

	
	
	
	return res;
}

TEST_CASE("prim's algo 1")
{

	auto start = std::chrono::high_resolution_clock::now();
	auto res = primsalgo({ {0,15,5,0,10},{15,0,0,75,0},{5,0,0,3,0},{0,0,3,0,40},{10,0,0,40,0} });
	CHECK_EQ(res,33);
	auto stop = std::chrono::high_resolution_clock::now();
	auto duration = std::chrono::duration_cast<std::chrono::microseconds>(stop - start);
	MESSAGE( duration.count());
}




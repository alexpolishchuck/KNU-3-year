#include <vector>
#include <iostream>
#include <thread>
#include <atomic>
#include <boost/multi_index_container.hpp>
#include <boost/multi_index/key_extractors.hpp>
#include <boost/multi_index/member.hpp>
#include <boost/multi_index/ordered_index.hpp>


struct test_struct
{
    test_struct(): token("CHECK")
    {}

    std::string token;
};

template<typename T>
class A
{
public: 
    using order_by_token = boost::multi_index::ordered_unique<
        boost::multi_index::member<T, std::string, &T::token>>;

    using blocks_cache_t = boost::multi_index::multi_index_container<
        T,
        boost::multi_index::indexed_by<order_by_token>>;

    blocks_cache_t kek;

};

int main()
{
    A<test_struct> a;
    
}



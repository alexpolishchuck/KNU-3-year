package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

type barrier struct {
	mutex    sync.Mutex
	counter  int
	max      int
	in       chan int
	out      chan int
	areEqual bool
	value    int
}

func initBarrier() *barrier {

	b := barrier{
		counter:  0,
		max:      3,
		in:       make(chan int, 1),
		out:      make(chan int, 1),
		areEqual: true,
		value:    -1,
	}
	//b.in <- 1
	return &b
}

func (b *barrier) before(sum int) {
	b.mutex.Lock()
	b.counter++
	if b.counter == 1 {
		b.value = sum
	} else {
		if b.value != sum || b.areEqual == false {
			b.areEqual = false
		}
	}
	if b.counter == b.max {
		time.Sleep(5000 * time.Millisecond)
		b.in <- 1
	}
	b.mutex.Unlock()
	<-b.in

	b.mutex.Lock()
	b.counter--
	if b.counter != 0 {
		b.in <- 1
	}
	b.mutex.Unlock()
}
func (b *barrier) after() {
	b.mutex.Lock()
	b.counter++
	if b.counter == b.max {
		time.Sleep(5000 * time.Millisecond)
		b.areEqual = true
		b.out <- 1
	}
	b.mutex.Unlock()
	<-b.out
	b.mutex.Lock()
	b.counter--
	if b.counter != 0 {
		b.out <- 1
	}
	b.mutex.Unlock()
}
func generateValues(n int) []int {
	res := make([]int, n)

	for i := 0; i < n; i++ {

		res[i] = rand.Intn(10)

	}
	return res
}

func job(b *barrier, id int) {
	size := 10
	arr := generateValues(size)

	for {
		sum := 0
		fmt.Printf("Thread is calculating sum, id: %d \n", id)
		for i := 0; i < size; i++ {
			sum = sum + arr[i]

		}
		fmt.Printf("Thread's sum %d, entered before, id: %d \n", sum, id)
		b.before(sum)
		b.mutex.Lock()
		if b.areEqual == false {
			i := rand.Intn(size)
			choice := rand.Intn(2)
			if choice == 0 {
				arr[i]++
				fmt.Printf("Thread reduced sum , id: %d \n", id)
			} else {
				arr[i]--
				fmt.Printf("Thread increased sum , id: %d \n", id)
			}
		} else {
			fmt.Printf("Thread stopped working, sum: %d , id: %d \n", sum, id)
			return
		}
		b.mutex.Unlock()
		fmt.Printf("Thread entered after, id: %d \n", id)
		b.after()
		time.Sleep(1000 * time.Millisecond)

	}

}

func main() {

	b := initBarrier()
	go job(b, 1)
	go job(b, 2)
	go job(b, 3)
	for {
	}
}

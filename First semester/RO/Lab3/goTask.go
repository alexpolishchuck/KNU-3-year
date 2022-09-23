package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

var mutex sync.Mutex
var items = []int{1, 1, 1}
var hasCheckedTable = []int{0, 0, 0}
var counter = 3

func generateValues() {
	for {
		mutex.Lock()
		if hasCheckedTable[0]*hasCheckedTable[1]*hasCheckedTable[2] == 0 {
			mutex.Unlock()
		} else {
			items = []int{1, 1, 1}
			for i := 0; i < 3; i++ {
				items[i] = rand.Intn(2)
				if items[i] == 0 {
					break
				} else {
					if i == 2 {
						items[i] = 0
					}
				}

			}

			fmt.Printf("Counter: %d, Values: %d,%d,%d \n", counter, items[0], items[1], items[2])
			hasCheckedTable = []int{0, 0, 0}
			time.Sleep(1000 * time.Millisecond)
			mutex.Unlock()
		}

	}
}

func smoker(idOfItem int) {
	for {
		mutex.Lock()
		if hasCheckedTable[idOfItem] == 0 {
			hasCheckedTable[idOfItem] = 1
			fmt.Printf("Smoker increased counter id: %d \n", idOfItem)
			if items[idOfItem] == 0 {
				fmt.Printf("Smoker is smoking, id: %d \n", idOfItem)
				time.Sleep(3000 * time.Millisecond)
				hasCheckedTable = []int{1, 1, 1}
				fmt.Printf("Smoker stopped smoking, id: %d \n", idOfItem)
			}
		}
		mutex.Unlock()
	}
}

func main() {
	go generateValues()
	go smoker(0)
	go smoker(1)
	go smoker(2)
	for {
	}
}

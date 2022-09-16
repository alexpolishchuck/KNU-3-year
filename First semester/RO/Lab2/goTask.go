package main

import (
	"fmt"
	"sync"
)

var mu sync.Mutex
var winners []int
var wg sync.WaitGroup

func defineWinner(pos int, monk1 int, monk2 int, powers []int) {

	mu.Lock()
	fmt.Printf("Pair: %d,%d \n", monk1, monk2)
	defer wg.Done()
	if powers[monk1] > powers[monk2] {
		fmt.Printf("Pair: %d > %d \n", powers[monk1], powers[monk2])
		winners[pos] = monk1
	} else {
		fmt.Printf("Pair: %d <= %d \n", powers[monk1], powers[monk2])
		winners[pos] = monk2
	}
	mu.Unlock()
}

func check(a []int) {
	a = append(a, 1)
}
func main() {
	const size = 8
	ci := []int{5, 7, 2, 4, 3, 4, 1, 5}
	tample1 := [size / 2]int{0, 2, 3, 5}
	tample2 := [size / 2]int{1, 4, 6, 7}

	fighters := make([]int, size)

	for i := 0; i < size; i++ {
		fighters[i] = tample1[i/2]
		fighters[i+1] = tample2[i/2]
		i++
	}
	fmt.Printf("Fighters: %v\n", fighters)
	fmt.Printf("Their power: %v\n", ci)
	for len(fighters) > 1 {
		newSize := len(fighters) / 2
		winners = make([]int, newSize)

		for i := 0; i < len(fighters); i += 2 {
			wg.Add(1)
			go defineWinner(i/2, fighters[i], fighters[i+1], ci)

		}
		wg.Wait()
		fighters = append([]int(nil), winners[:newSize]...)
		fmt.Printf("%v\n", fighters)
	}
}

#include <iostream>
#include "mpi.h"
#include <chrono>
#include <thread>
#include <stdio.h>
//mpiexec -n 2 RO.exe

const int SIZE = 400;


int** createArray() {

	int** arr = new int* [SIZE];

	for (auto i = 0; i < SIZE; i++)
		arr[i] = new int[SIZE];

	return arr;

}
void fillMatrix( int** matrix) {
	

	for (int i = 0; i < SIZE; i++)
	{
		for (int j = 0; j < SIZE; j++)
			matrix[i][j] = rand() % 10;

	}


}

void printArray(int** matrix) {
	std::cout << "RESULT\n";
	for (int i = 0; i < SIZE; i++) {
		for (int j = 0; j < SIZE; j++)
			std::cout << matrix[i][j] << " ";
			std::cout << "\n";

	}
}

void deleteArray(int**&matrix) {
	for (int i = 0; i < SIZE; i++)
		delete[] matrix[i];
	delete[] matrix;
}

void NaiveMultiplication(int& argc, char** &argv) {
	
	int rank, numberOfThreads;

	int** matrixA;
	int** matrixB;
	int** matrixAB;


	MPI_Init(&argc, &argv);
	MPI_Comm_size(MPI_COMM_WORLD, &numberOfThreads);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	if (rank == 0) {
		std::cout << "Naive multiplication\n";
		srand(time(NULL));
		matrixA = createArray();
		matrixB = createArray();
		matrixAB = createArray();
		fillMatrix(matrixA);
		fillMatrix(matrixB);
		numberOfThreads--;
		int tasks = SIZE / numberOfThreads;
		int offset = 0;

		std::cout << "A MATRIX:\n";
			printArray(matrixA);
		std::cout << "B MATRIX:\n";
			printArray(matrixB);
		double t1 = -1, t2 = -1;
		for (int i = 1; i <= numberOfThreads; i++)
		{
			if (i == 1)
				t1 = MPI_Wtime();
			MPI_Send(&offset, 1, MPI_INT, i, 1, MPI_COMM_WORLD);
			MPI_Send(&tasks, 1, MPI_INT, i, 1, MPI_COMM_WORLD);
			for (int j = offset; j < offset + tasks; j++)
				MPI_Send(&(matrixA[j][0]), SIZE, MPI_INT, i, 1, MPI_COMM_WORLD);
			for (int j = 0; j < SIZE; j++)
				MPI_Send(&(matrixB[j][0]), SIZE, MPI_INT, i, 1, MPI_COMM_WORLD);
			offset += tasks;
		}

		MPI_Status status;
		for (int i = 1; i <= numberOfThreads; i++)
		{
			MPI_Recv(&offset, 1, MPI_INT, i, 2, MPI_COMM_WORLD, &status);
			for (int j = offset; j < offset + tasks; j++)
				MPI_Recv(&matrixAB[j][0], SIZE, MPI_INT, i, 2, MPI_COMM_WORLD, &status);
			if (i == numberOfThreads)
				t2 = MPI_Wtime();
		}
		std::cout << "TIME: " << t2 - t1 << std::endl;
		std::cout << "AxB: \n";
			printArray(matrixAB);
	}
	else if (rank > 0) {
		MPI_Status status;
		int offset, tasks;
		matrixAB = createArray();
		matrixA = createArray();
		matrixB = createArray();
		MPI_Recv(&offset, 1, MPI_INT, 0, 1, MPI_COMM_WORLD, &status);
		MPI_Recv(&tasks, 1, MPI_INT, 0, 1, MPI_COMM_WORLD, &status);
		for (int i = 0; i < tasks; i++)
			MPI_Recv(&(matrixA[i][0]), SIZE, MPI_INT, 0, 1, MPI_COMM_WORLD, &status);
		for (int i = 0; i < SIZE; i++)
			MPI_Recv(&(matrixB[i][0]), SIZE, MPI_INT, 0, 1, MPI_COMM_WORLD, &status);

		for (int i = 0; i < tasks; i++) {
			for (int j = 0; j < SIZE; j++) {
				matrixAB[i][j] = 0;
				for (int k = 0; k < SIZE; k++) {
					matrixAB[i][j] = matrixAB[i][j] + matrixA[i][k] * matrixB[k][j];
				}
			}
		}
		MPI_Send(&offset, 1, MPI_INT, 0, 2, MPI_COMM_WORLD);
		for (int i = 0; i < tasks; i++)
			MPI_Send(&(matrixAB[i][0]), SIZE, MPI_INT, 0, 2, MPI_COMM_WORLD);
		deleteArray(matrixAB);
		deleteArray(matrixA);
		deleteArray(matrixB);
	}
	MPI_Finalize();

	if (rank == 0) {
		deleteArray(matrixA);
		deleteArray(matrixB);
		deleteArray(matrixAB);
		std::cout << "-------------------------\n";
	}
}

void FoxMethod(int& argc, char** &argv) {
	
	int rank, numberOfThreads;

	int** matrixA;
	int** matrixB;
	int** matrixAB;


	MPI_Init(&argc, &argv);
	MPI_Comm_size(MPI_COMM_WORLD, &numberOfThreads);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	if (rank == 0) {
		std::cout << "Cannon's method multiplication\n";
		srand(time(NULL));
		matrixA = createArray();
		matrixB = createArray();
		matrixAB = createArray();
		fillMatrix(matrixA);
		fillMatrix(matrixB);
		numberOfThreads--;
		int tasks = SIZE / numberOfThreads;
		int offseti = 0;
		std::cout << "A MATRIX:\n";
//		printArray(matrixA);
		std::cout << "B MATRIX:\n";
//		printArray(matrixB);
		double t1 = -1, t2 = -1;
		for (int i = 1; i <= numberOfThreads; i++)
		{
			if (i == 1)
				t1 = MPI_Wtime();
			
			MPI_Send(&offseti,1,MPI_INT,i,1, MPI_COMM_WORLD);
			

			int rows = 0;
			if (i < numberOfThreads) {
				rows = offseti + tasks;
				MPI_Send(&tasks, 1, MPI_INT, i, 1, MPI_COMM_WORLD);
			}
			else {
				rows = SIZE- offseti;
				MPI_Send(&rows, 1, MPI_INT, i, 1, MPI_COMM_WORLD);
				rows = SIZE;
			}
			for(int j=offseti; j<rows;j++)
			    MPI_Send(&(matrixA[j][0]), SIZE, MPI_INT, i, 1, MPI_COMM_WORLD);
			for(int j=0;j<SIZE;j++)
				MPI_Send(&(matrixB[j][0]), SIZE, MPI_INT, i, 1, MPI_COMM_WORLD);
			offseti += tasks;
			
		}

		MPI_Status status;
		for (int i = 1; i <= numberOfThreads; i++)
		{
			MPI_Recv(&offseti, 1, MPI_INT, i, 2, MPI_COMM_WORLD, &status);			
			for (int j = offseti; j < offseti + tasks; j++)
				MPI_Recv(&matrixAB[j][0], SIZE, MPI_INT, i, 2, MPI_COMM_WORLD, &status);


			if (i == numberOfThreads)
				t2 = MPI_Wtime();
		}
		std::cout << "TIME: " << t2 - t1 << std::endl;
		std::cout << "AxB: \n";
	//	printArray(matrixAB);
	}
	else if (rank > 0) {
		MPI_Status status;
		int offseti, tasks, shift=0;
		matrixAB = createArray();
		matrixA = createArray();
		matrixB = createArray();
		MPI_Recv(&offseti, 1, MPI_INT, 0, 1, MPI_COMM_WORLD, &status);
		MPI_Recv(&tasks, 1, MPI_INT, 0, 1, MPI_COMM_WORLD, &status);
		for (int i = offseti; i < offseti + tasks; i++)
			MPI_Recv(&(matrixA[i][0]), SIZE, MPI_INT, 0, 1, MPI_COMM_WORLD,&status);
		for (int i = 0; i < SIZE; i++)
			MPI_Recv(&(matrixB[i][0]), SIZE, MPI_INT, 0, 1, MPI_COMM_WORLD, &status);
		for (int i = offseti; i < offseti + tasks; i++)
			for (int j = 0; j < SIZE; j++)
				matrixAB[i][j] = 0;

		for (int k = 0; k < SIZE; k++) {
			for (int i = offseti; i < offseti + tasks; i++)
			{
				for (int j = 0; j < SIZE; j++)
				{
					matrixAB[i][j] += matrixA[i][(i + shift) % SIZE] * matrixB[(i + shift) % SIZE][j];
				}
			}
			shift++;
		}
		MPI_Send(&offseti, 1, MPI_INT, 0, 2, MPI_COMM_WORLD);
		for (int j = offseti; j < offseti + tasks; j++)
			MPI_Send(&matrixAB[j][0], SIZE, MPI_INT, 0, 2, MPI_COMM_WORLD);
		deleteArray(matrixAB);
		deleteArray(matrixA);
		deleteArray(matrixB);
	}
	MPI_Finalize();

	if (rank == 0) {
		deleteArray(matrixA);
		deleteArray(matrixB);
		deleteArray(matrixAB);
		std::cout << "-------------------------\n";
	}
}

void CannonsMethod(int& argc, char**& argv) {

	int rank, numberOfThreads;

	int** matrixA;
	int** matrixB;
	int** matrixAB;


	MPI_Init(&argc, &argv);
	MPI_Comm_size(MPI_COMM_WORLD, &numberOfThreads);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	if (rank == 0) {
		std::cout << "Fox method multiplication\n";
		srand(time(NULL));
		matrixA = createArray();
		matrixB = createArray();
		matrixAB = createArray();
		fillMatrix(matrixA);
		fillMatrix(matrixB);
		numberOfThreads--;
		int tasks = SIZE / numberOfThreads;
		int offseti = 0;
		std::cout << "A MATRIX:\n";
	//	printArray(matrixA);
		std::cout << "B MATRIX:\n";
	//	printArray(matrixB);
		double t1 = -1, t2 = -1;
		for (int i = 1; i <= numberOfThreads; i++)
		{
			if (i == 1)
				t1 = MPI_Wtime();

			MPI_Send(&offseti, 1, MPI_INT, i, 1, MPI_COMM_WORLD);


			int rows = 0;
			if (i < numberOfThreads) {
				rows = offseti + tasks;
				MPI_Send(&tasks, 1, MPI_INT, i, 1, MPI_COMM_WORLD);
			}
			else {
				rows = SIZE - offseti;
				MPI_Send(&rows, 1, MPI_INT, i, 1, MPI_COMM_WORLD);
				rows = SIZE;
			}
			for (int j = offseti; j < rows; j++)
				MPI_Send(&(matrixA[j][0]), SIZE, MPI_INT, i, 1, MPI_COMM_WORLD);
			for (int j = 0; j < SIZE; j++)
				MPI_Send(&(matrixB[j][0]), SIZE, MPI_INT, i, 1, MPI_COMM_WORLD);
			offseti += tasks;

		}

		MPI_Status status;
		for (int i = 1; i <= numberOfThreads; i++)
		{
			MPI_Recv(&offseti, 1, MPI_INT, i, 2, MPI_COMM_WORLD, &status);
			for (int j = offseti; j < offseti + tasks; j++)
				MPI_Recv(&matrixAB[j][0], SIZE, MPI_INT, i, 2, MPI_COMM_WORLD, &status);


			if (i == numberOfThreads)
				t2 = MPI_Wtime();
		}
		std::cout << "TIME: " << t2 - t1 << std::endl;
		std::cout << "AxB: \n";
	//		printArray(matrixAB);
	}
	else if (rank > 0) {
		MPI_Status status;
		int offseti, tasks, shift = 1;
		matrixAB = createArray();
		matrixA = createArray();
		matrixB = createArray();
		MPI_Recv(&offseti, 1, MPI_INT, 0, 1, MPI_COMM_WORLD, &status);
		MPI_Recv(&tasks, 1, MPI_INT, 0, 1, MPI_COMM_WORLD, &status);
		for (int i = offseti; i < offseti + tasks; i++)
			MPI_Recv(&(matrixA[i][0]), SIZE, MPI_INT, 0, 1, MPI_COMM_WORLD, &status);
		for (int i = 0; i < SIZE; i++)
			MPI_Recv(&(matrixB[i][0]), SIZE, MPI_INT, 0, 1, MPI_COMM_WORLD, &status);
		for (int i = offseti; i < offseti + tasks; i++)
			for (int j = 0; j < SIZE; j++)
				matrixAB[i][j] = 0;

			for (int i = offseti; i < offseti + tasks; i++)
			{
				for (int j = 0; j < SIZE; j++)
				{
					matrixAB[i][j] += matrixA[i][(j + i) % SIZE] * matrixB[(i + j) % SIZE][j];
				}
			}
			
		
		for (int k = 1; k < SIZE; k++) {
			for (int i = offseti; i < offseti + tasks; i++)
			{
				for (int j = 0; j < SIZE; j++)
				{
					matrixAB[i][j] += matrixA[i][(j+ i + shift) % SIZE] * matrixB[( j + i + shift) % SIZE][j];
				}
			}
			shift++;
		}
		MPI_Send(&offseti, 1, MPI_INT, 0, 2, MPI_COMM_WORLD);
		for (int j = offseti; j < offseti + tasks; j++)
			MPI_Send(&matrixAB[j][0], SIZE, MPI_INT, 0, 2, MPI_COMM_WORLD);
		deleteArray(matrixAB);
		deleteArray(matrixA);
		deleteArray(matrixB);
	}
	MPI_Finalize();

	if (rank == 0) {
		deleteArray(matrixA);
		deleteArray(matrixB);
		deleteArray(matrixAB);
		std::cout << "-------------------------\n";
	}

}

int main(int argc, char** argv)
{
//	NaiveMultiplication(argc,argv);
	 FoxMethod(argc, argv);
//	CannonsMethod(argc, argv);
	return 0;
}
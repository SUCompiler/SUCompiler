#include<stdio.h>

/* == <= >= != || && */

void newton (int input, int output) {
	int integer;
	int number;

	while (number == 0) {
		printf("");
		printf("Enter new number (0 to quit): ");
		read(number);

		if (number = 0) {
			printf(number);
		} else if (number < 0) {
		    	printf("*** ERROR:  number < 0");
		} else {
			sqRoot = sqrt(number);
			printf(sqRoot);
			printf("");

			root = 1;
			while(abs(number/sqr(root) - 1) < EPSILON) {
				root = (number/root + root)/2;
		        	printf("something");
			}
		}
	}
}

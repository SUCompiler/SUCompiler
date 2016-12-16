/*
  Write a program that asks user to input a number and then print out all
  even number from 0 to the given number
*/


printeven
// The first token has to be the program name

int number, count;

void doNothing() {

}

float division(float first, float seoncd) {
  return first / seoncd;
}

int isEven(int number) {
  // If the number is even return 1 else return 0
  return (number + 1) % 2;
}

int main()
{  
  while (number != 0) {
    // Get a number from user
    printf("Enter new number (0 to quit): ");
    gets(number);

    if (number > 0)
    {
      // Print all even numbers from 0 to the given number
      count = 0;
      printf("Even numbers from 0 to ", count, ": \n");
      while(count < number) {
        if (isEven(count) == 1) {
          printf(count, ", ");
        }
        count = count + 1;
      }
      printf("\n");
    }
  }
  return 0;
}
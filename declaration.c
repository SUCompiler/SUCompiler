int i, x;
bool boolOne, boolTwo;

bool isRunning() {
  return boolOne && boolTwo;
}

int addition(int a, int b) {
  return a + b;  
}

void loopTo(int num) {
  i = 0;

  while(i < num) {
    writeln("Index: ", i);
    i = i + 1;
  }
}


void multiply(bool boolParam) {
	i = 0;

	while(i < 5) {
		i = i + 1;
	}
}

int main() {
	i = 0;
  writeln("The square root of ", i);
  i = addition(5, 9);
  writeln("The square root of ", i);
  loopTo(i);
	return i;
}
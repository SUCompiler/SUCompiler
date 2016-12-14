declaration
int i, x;
bool boolOne, boolTwo;
float float1, float2;

bool isRunning() {
  return boolOne;
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
  float1= 0.1;
	i = 0;
  writeln("The square root of ", i);
  i = addition(5, 9);
  writeln("The square root of ", i);
  loopTo(i);
	return i;
}
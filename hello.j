.class public hello
.super java/lang/Object

.field private static _runTimer LRunTimer;
.field private static _standardIn LPascalTextIn;

.field private static boolone Z
.field private static booltwo Z
.field private static float1 F
.field private static float2 F
.field private static i I
.field private static x I

.method public <init>()V

	aload_0
	invokenonvirtual	java/lang/Object/<init>()V
	return

.limit locals 1
.limit stack 1
.end method

.method private static isrunning()Z

.var 1 is isrunning Z


	nop

	iload_1
	ireturn

.limit locals 2
.limit stack 1
.end method

.method private static addition(II)I

.var 0 is a I
.var 1 is b I
.var 2 is addition I


.line 9
	iload_0
	iload_1
	iadd
	istore_2

	iload_2
	ireturn

.limit locals 3
.limit stack 2
.end method

.method private static loopto(I)V

.var 0 is num I


.line 13
	iconst_0
	putstatic	hello/i I
.line 15
L001:
	getstatic	hello/i I
	iload_0
	if_icmplt	L003
	iconst_0
	goto	L004
L003:
	iconst_1
L004:
	iconst_1
	ixor
	ifne	L002
.line 16
	getstatic	java/lang/System/out Ljava/io/PrintStream;
	ldc	"Index: %d\n"
	iconst_1
	anewarray	java/lang/Object
	dup
	iconst_0
	getstatic	hello/i I
	invokestatic	java/lang/Integer.valueOf(I)Ljava/lang/Integer;
	aastore
	invokestatic	java/lang/String/format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
	invokevirtual	java/io/PrintStream.print(Ljava/lang/String;)V
.line 17
	getstatic	hello/i I
	iconst_1
	iadd
	putstatic	hello/i I
	goto	L001
L002:

	return

.limit locals 1
.limit stack 7
.end method

.method private static multiply(Z)V

.var 0 is boolparam Z


.line 23
	iconst_0
	putstatic	hello/i I
.line 25
L005:
	getstatic	hello/i I
	iconst_5
	if_icmplt	L007
	iconst_0
	goto	L008
L007:
	iconst_1
L008:
	iconst_1
	ixor
	ifne	L006
.line 26
	getstatic	hello/i I
	iconst_1
	iadd
	putstatic	hello/i I
	goto	L005
L006:

	return

.limit locals 1
.limit stack 2
.end method

.method private static main()I

.var 1 is main I


.line 31
	ldc	0.1
	putstatic	hello/float1 F
.line 32
	iconst_0
	putstatic	hello/i I
.line 33
	getstatic	java/lang/System/out Ljava/io/PrintStream;
	ldc	"The square root of %d\n"
	iconst_1
	anewarray	java/lang/Object
	dup
	iconst_0
	getstatic	hello/i I
	invokestatic	java/lang/Integer.valueOf(I)Ljava/lang/Integer;
	aastore
	invokestatic	java/lang/String/format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
	invokevirtual	java/io/PrintStream.print(Ljava/lang/String;)V
.line 34
	iconst_5
	bipush	9
	invokestatic	hello/addition(II)I
	putstatic	hello/i I
.line 35
	getstatic	java/lang/System/out Ljava/io/PrintStream;
	ldc	"The square root of %d\n"
	iconst_1
	anewarray	java/lang/Object
	dup
	iconst_0
	getstatic	hello/i I
	invokestatic	java/lang/Integer.valueOf(I)Ljava/lang/Integer;
	aastore
	invokestatic	java/lang/String/format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
	invokevirtual	java/io/PrintStream.print(Ljava/lang/String;)V
.line 36
	getstatic	hello/i I
	invokestatic	hello/loopto(I)V
.line 37
	getstatic	java/lang/System/out Ljava/io/PrintStream;
	ldc	"%f\n"
	iconst_1
	anewarray	java/lang/Object
	dup
	iconst_0
	getstatic	hello/float1 F
	invokestatic	java/lang/Float.valueOf(F)Ljava/lang/Float;
	aastore
	invokestatic	java/lang/String/format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
	invokevirtual	java/io/PrintStream.print(Ljava/lang/String;)V
.line 38
	getstatic	hello/i I
	istore_1

	iload_1
	ireturn

.limit locals 2
.limit stack 7
.end method

.method public static main([Ljava/lang/String;)V

	new	RunTimer
	dup
	invokenonvirtual	RunTimer/<init>()V
	putstatic	hello/_runTimer LRunTimer;
	new	PascalTextIn
	dup
	invokenonvirtual	PascalTextIn/<init>()V
	putstatic	hello/_standardIn LPascalTextIn;


.line 39
	invokestatic	hello/main()I

	getstatic	hello/_runTimer LRunTimer;
	invokevirtual	RunTimer.printElapsedTime()V

	return

.limit locals 1
.limit stack 3
.end method

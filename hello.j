.class public hello
.super java/lang/Object

.field private static _runTimer LRunTimer;
.field private static _standardIn LPascalTextIn;

.field private static boolone Z
.field private static booltwo Z
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


.line 5
	getstatic	hello/booltwo Z
	istore_1

	iload_1
	ireturn

.limit locals 2
.limit stack 1
.end method

.method private static multiply(Z)V

.var 0 is boolparam Z


.line 9
	iconst_0
	putstatic	hello/i I
.line 11
L001:
	getstatic	hello/i I
	iconst_5
	if_icmplt	L003
	iconst_0
	goto	L004
L003:
	iconst_1
L004:
	iconst_1
	ixor
	ifne	L002
.line 12
	getstatic	hello/i I
	iconst_1
	iadd
	putstatic	hello/i I
	goto	L001
L002:

	return

.limit locals 1
.limit stack 2
.end method

.method private static main()I

.var 1 is main I


.line 17
	iconst_0
	putstatic	hello/i I
.line 18
	invokestatic	hello/isrunning()Z
	putstatic	hello/boolone Z
.line 19
	getstatic	hello/i I
	istore_1

	iload_1
	ireturn

.limit locals 2
.limit stack 1
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


.line 100
	invokestatic	hello/main()I

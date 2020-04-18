declare i8* @malloc(i64)
declare void @print(i8*)
declare void @println(i8*)
declare void @printInt(i32)
declare void @printlnInt(i32)
declare i8* @toString(i32)
declare i32 @getInt()
declare i8* @getString()
declare i32 @_string_length(i8*)
declare i8* @_string_substring(i8*, i32, i32)
declare i32 @_string_parseInt(i8*)
declare i32 @_string_ord(i8*, i32)
declare i8* @_string_concatenate(i8*, i8*)
declare i1 @_string_eq(i8*,i8*)
declare i1 @_string_ne(i8*, i8*)
declare i1 @_string_slt(i8*, i8*)
declare i1 @_string_sle(i8*, i8*)
declare i1 @_string_sgt(i8*, i8*)
declare i1 @_string_sge(i8*, i8*)

define i32 @gcd(i32 %x, i32 %y){
func_init:
%x.1 = alloca i32, align 4
store i32 %x, i32* %x.1
%y.1 = alloca i32, align 4
store i32 %y, i32* %y.1
%ret = alloca i32, align 4
%load_x = load i32, i32* %x.1
%load_y = load i32, i32* %y.1
%T = srem i32 %load_x, %load_y
%T.1 = icmp eq i32 %T, 0
br i1 %T.1, label %if_taken, label %if_notTaken

if_taken:
%load_y.1 = load i32, i32* %y.1
%load_ret.1 = load i32, i32* %ret
store i32 %load_y.1, i32* %ret
br label %retBlock

if_notTaken:
%load_x.1 = load i32, i32* %x.1
%load_y.2 = load i32, i32* %y.1
%T.2 = srem i32 %load_x.1, %load_y.2
%load_y.3 = load i32, i32* %y.1
%call_gcd = call i32 @gcd(i32 %load_y.3, i32 %T.2)
%load_ret.2 = load i32, i32* %ret
store i32 %call_gcd, i32* %ret
br label %retBlock

retBlock:
%load_ret = load i32, i32* %ret
ret i32 %load_ret

}

define i32 @main(){
func_init.1:
%ret.1 = alloca i32, align 4
store i32 0, i32* %ret.1
call void @__global_init()
%call_gcd.1 = call i32 @gcd(i32 10, i32 1)
%call_toString = call i8* @toString(i32 %call_gcd.1)
call void @println(i8* %call_toString)
%call_gcd.2 = call i32 @gcd(i32 34986, i32 3087)
%call_toString.1 = call i8* @toString(i32 %call_gcd.2)
call void @println(i8* %call_toString.1)
%call_gcd.3 = call i32 @gcd(i32 2907, i32 1539)
%call_toString.2 = call i8* @toString(i32 %call_gcd.3)
call void @println(i8* %call_toString.2)
%load_ret.4 = load i32, i32* %ret.1
store i32 0, i32* %ret.1
br label %retBlock.1

retBlock.1:
%load_ret.3 = load i32, i32* %ret.1
ret i32 %load_ret.3

}

define void @__global_init(){
init:
ret void

}


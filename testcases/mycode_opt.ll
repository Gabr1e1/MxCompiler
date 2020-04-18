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
%T = srem i32 %x, %y
%T.1 = icmp eq i32 %T, 0
br i1 %T.1, label %if_taken, label %if_notTaken

if_taken:
br label %retBlock

if_notTaken:
%T.2 = srem i32 %x, %y
%call_gcd = call i32 @gcd(i32 %y, i32 %T.2)
br label %retBlock

retBlock:
%ret.2 = phi i32 [%call_gcd, %if_notTaken], [%y, %if_taken]
ret i32 %ret.2

}

define i32 @main(){
func_init.1:
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
br label %retBlock.1

retBlock.1:
ret i32 0

}

define void @__global_init(){
init:
ret void

}


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

@.conststr = constant [4 x i8] c"aaa\00", align 1
@.conststr.1 = constant [6 x i8] c"bbbbb\00", align 1
define i32 @main(){
retBlock:
call void @__global_init()
%T = getelementptr [4 x i8], [4 x i8]* @.conststr, i32 0, i32 0
%T.1 = getelementptr [6 x i8], [6 x i8]* @.conststr.1, i32 0, i32 0
%call__string_concatenate = call i8* @_string_concatenate(i8* %T, i8* %T.1)
%call__string_length = call i32 @_string_length(i8* %call__string_concatenate)
%call__string_ord = call i32 @_string_ord(i8* %call__string_concatenate, i32 5)
%T.2 = add i32 %call__string_length, %call__string_ord
ret i32 %T.2

}

define void @__global_init(){
init:
ret void

}


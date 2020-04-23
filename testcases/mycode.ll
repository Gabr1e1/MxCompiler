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
func_init:
%ret = alloca i32, align 4
store i32 0, i32* %ret
call void @__global_init()
%s1 = alloca i8*, align 8
%T = getelementptr [4 x i8], [4 x i8]* @.conststr, i32 0, i32 0
%load_s1 = load i8*, i8** %s1
store i8* %T, i8** %s1
%s2 = alloca i8*, align 8
%T.1 = getelementptr [6 x i8], [6 x i8]* @.conststr.1, i32 0, i32 0
%load_s2 = load i8*, i8** %s2
store i8* %T.1, i8** %s2
%s3 = alloca i8*, align 8
%load_s1.1 = load i8*, i8** %s1
%load_s2.1 = load i8*, i8** %s2
%call__string_concatenate = call i8* @_string_concatenate(i8* %load_s1.1, i8* %load_s2.1)
%load_s3 = load i8*, i8** %s3
store i8* %call__string_concatenate, i8** %s3
%load_s3.1 = load i8*, i8** %s3
%call__string_length = call i32 @_string_length(i8* %load_s3.1)
%load_s3.2 = load i8*, i8** %s3
%call__string_ord = call i32 @_string_ord(i8* %load_s3.2, i32 5)
%T.2 = add i32 %call__string_length, %call__string_ord
%load_ret.1 = load i32, i32* %ret
store i32 %T.2, i32* %ret
br label %retBlock

retBlock:
%load_ret = load i32, i32* %ret
ret i32 %load_ret

}

define void @__global_init(){
init:
ret void

}


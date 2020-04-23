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

@.conststr = constant [2 x i8] c" \00", align 1
@.conststr.1 = constant [1 x i8] c"\00", align 1
define i32 @partition(i32* %a, i32 %p, i32 %r){
func_init:
%T = getelementptr i32, i32* %a, i32 %r
%load_T = load i32, i32* %T
%T.1 = sub i32 %p, 1
br label %for_cond

for_cond:
%j.1 = phi i32 [%p, %func_init], [%T.10, %for_incr]
%i.3 = phi i32 [%T.1, %func_init], [%i.2, %for_incr]
%T.2 = icmp slt i32 %j.1, %r
br i1 %T.2, label %for_incr, label %retBlock

for_incr:
%i.2 = phi i32 [%i.3, %for_incr], [%T.5, %for_incr]
%T.3 = getelementptr i32, i32* %a, i32 %j.1
%load_T.1 = load i32, i32* %T.3
%T.4 = icmp sle i32 %load_T.1, %load_T
%T.5 = add i32 %i.3, 1
%T.6 = getelementptr i32, i32* %a, i32 %T.5
%load_T.2 = load i32, i32* %T.6
%T.7 = getelementptr i32, i32* %a, i32 %T.5
%T.8 = getelementptr i32, i32* %a, i32 %j.1
%load_T.3 = load i32, i32* %T.8
store i32 %load_T.3, i32* %T.7
%T.9 = getelementptr i32, i32* %a, i32 %j.1
store i32 %load_T.2, i32* %T.9
%T.10 = add i32 %j.1, 1
br label %for_cond

retBlock:
%T.11 = add i32 %i.3, 1
%T.12 = getelementptr i32, i32* %a, i32 %T.11
%load_T.6 = load i32, i32* %T.12
%T.13 = add i32 %i.3, 1
%T.14 = getelementptr i32, i32* %a, i32 %T.13
%T.15 = getelementptr i32, i32* %a, i32 %r
%load_T.7 = load i32, i32* %T.15
store i32 %load_T.7, i32* %T.14
%T.16 = getelementptr i32, i32* %a, i32 %r
store i32 %load_T.6, i32* %T.16
%T.17 = add i32 %i.3, 1
ret i32 %T.17

}

define void @quick_sort(i32* %a.2, i32 %p.2, i32 %r.2){
func_init.1:
%T.18 = icmp sge i32 %p.2, %r.2
br i1 %T.18, label %if_taken.1, label %if_after.1

if_taken.1:
br label %retBlock.1

if_after.1:
%call_partition = call i32 @partition(i32* %a.2, i32 %p.2, i32 %r.2)
%T.19 = sub i32 %call_partition, 1
call void @quick_sort(i32* %a.2, i32 %p.2, i32 %T.19)
%T.20 = add i32 %call_partition, 1
call void @quick_sort(i32* %a.2, i32 %T.20, i32 %r.2)
br label %retBlock.1

retBlock.1:
ret void

}

define void @quick_sort_inf(i32* %a.4){
retBlock.2:
%M = bitcast i32* %a.4 to i32*
%T.21 = getelementptr i32, i32* %M, i32 -1
%load_T.10 = load i32, i32* %T.21
%T.22 = sub i32 %load_T.10, 1
call void @quick_sort(i32* %a.4, i32 0, i32 %T.22)
ret void

}

define i32 @main(){
func_init.3:
call void @__global_init()
%call_getInt = call i32 @getInt()
%T.23 = mul i32 4, %call_getInt
%T.24 = add i32 %T.23, 4
%M.1 = sext i32 %T.24 to i64
%call_malloc = call i8* @malloc(i64 %M.1)
%M.2 = bitcast i8* %call_malloc to i32*
store i32 %call_getInt, i32* %M.2
%T.25 = getelementptr i32, i32* %M.2, i32 1
%M.3 = bitcast i32* %T.25 to i32*
br label %for_cond.1

for_cond.1:
%i.1 = phi i32 [0, %func_init.3], [%T.28, %for_incr.1]
%T.26 = icmp slt i32 %i.1, %call_getInt
br i1 %T.26, label %for_incr.1, label %for_after.1

for_incr.1:
%T.27 = getelementptr i32, i32* %M.3, i32 %i.1
%call_getInt.1 = call i32 @getInt()
store i32 %call_getInt.1, i32* %T.27
%T.28 = add i32 %i.1, 1
br label %for_cond.1

for_after.1:
call void @quick_sort_inf(i32* %M.3)
br label %for_cond.2

for_cond.2:
%i.1.1 = phi i32 [0, %for_after.1], [%T.32, %for_incr.2]
%T.29 = icmp slt i32 %i.1.1, %call_getInt
br i1 %T.29, label %for_incr.2, label %retBlock.3

for_incr.2:
%T.30 = getelementptr i32, i32* %M.3, i32 %i.1.1
%load_T.12 = load i32, i32* %T.30
%call_toString = call i8* @toString(i32 %load_T.12)
%T.31 = getelementptr [2 x i8], [2 x i8]* @.conststr, i32 0, i32 0
%call__string_concatenate = call i8* @_string_concatenate(i8* %call_toString, i8* %T.31)
call void @print(i8* %call__string_concatenate)
%T.32 = add i32 %i.1.1, 1
br label %for_cond.2

retBlock.3:
%T.33 = getelementptr [1 x i8], [1 x i8]* @.conststr.1, i32 0, i32 0
call void @println(i8* %T.33)
ret i32 0

}

define void @__global_init(){
init:
ret void

}


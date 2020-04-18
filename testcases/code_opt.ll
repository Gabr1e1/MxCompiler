; ModuleID = './testcases/mycode.ll'
source_filename = "./testcases/mycode.ll"

@a = global i32** null, align 8
@str = global i8** null, align 8
@.conststr = constant [5 x i8] c"str1\00", align 1
@.conststr.1 = constant [5 x i8] c"str2\00", align 1
@.conststr.2 = constant [5 x i8] c"str3\00", align 1
@.conststr.3 = constant [5 x i8] c"str4\00", align 1
@.conststr.4 = constant [5 x i8] c"str5\00", align 1
@.conststr.5 = constant [5 x i8] c"str6\00", align 1
@.conststr.6 = constant [5 x i8] c"str7\00", align 1
@.conststr.7 = constant [5 x i8] c"str8\00", align 1
@.conststr.8 = constant [5 x i8] c"str9\00", align 1
@.conststr.9 = constant [6 x i8] c"str10\00", align 1

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

declare i1 @_string_eq(i8*, i8*)

declare i1 @_string_ne(i8*, i8*)

declare i1 @_string_slt(i8*, i8*)

declare i1 @_string_sle(i8*, i8*)

declare i1 @_string_sgt(i8*, i8*)

declare i1 @_string_sge(i8*, i8*)

define i32 @main() {
func_init:
  call void @__global_init()
  br label %for_cond.1

for_cond.1:                                       ; preds = %for_incr, %func_init
  %i.1.0 = phi i32 [ 0, %func_init ], [ %T.38, %for_incr ]
  %T.12 = icmp sle i32 %i.1.0, 29
  br i1 %T.12, label %for_body.1, label %for_after.1

for_body.1:                                       ; preds = %for_cond.1
  %load_str.1 = load i8**, i8*** @str
  %T.13 = getelementptr i8*, i8** %load_str.1, i32 %i.1.0
  %load_a.1 = load i32**, i32*** @a
  %T.14 = getelementptr i32*, i32** %load_a.1, i32 %i.1.0
  %load_T.1 = load i32*, i32** %T.14
  %T.15 = getelementptr i32, i32* %load_T.1, i32 0
  %load_T.2 = load i32, i32* %T.15
  %call_toString = call i8* @toString(i32 %load_T.2)
  store i8* %call_toString, i8** %T.13
  br label %for_cond.2

for_incr:                                         ; preds = %for_after.2
  %T.38 = add i32 %i.1.0, 1
  br label %for_cond.1

for_after.1:                                      ; preds = %for_cond.1
  br label %retBlock

for_cond.2:                                       ; preds = %for_incr.1, %for_body.1
  %j.1 = phi i32 [ 0, %for_body.1 ], [ %T.27, %for_incr.1 ]
  %sum.1 = phi i32 [ 0, %for_body.1 ], [ %sum.3, %for_incr.1 ]
  %T.16 = icmp slt i32 %j.1, %i.1.0
  br i1 %T.16, label %for_body.2, label %for_after.2

for_body.2:                                       ; preds = %for_cond.2
  %T.17 = and i32 %j.1, 1
  %T.18 = icmp eq i32 %T.17, 0
  br i1 %T.18, label %if_taken, label %if_after

for_incr.1:                                       ; preds = %if_after.1
  %T.27 = add i32 %j.1, 1
  br label %for_cond.2

for_after.2:                                      ; preds = %for_cond.2
  %T.28 = getelementptr [5 x i8], [5 x i8]* @.conststr, i32 0, i32 0
  %T.29 = getelementptr [5 x i8], [5 x i8]* @.conststr.1, i32 0, i32 0
  %call__string_concatenate = call i8* @_string_concatenate(i8* %T.28, i8* %T.29)
  %T.30 = getelementptr [5 x i8], [5 x i8]* @.conststr.2, i32 0, i32 0
  %call__string_concatenate.1 = call i8* @_string_concatenate(i8* %call__string_concatenate, i8* %T.30)
  %T.31 = getelementptr [5 x i8], [5 x i8]* @.conststr.3, i32 0, i32 0
  %call__string_concatenate.2 = call i8* @_string_concatenate(i8* %call__string_concatenate.1, i8* %T.31)
  %T.32 = getelementptr [5 x i8], [5 x i8]* @.conststr.4, i32 0, i32 0
  %call__string_concatenate.3 = call i8* @_string_concatenate(i8* %call__string_concatenate.2, i8* %T.32)
  %T.33 = getelementptr [5 x i8], [5 x i8]* @.conststr.5, i32 0, i32 0
  %call__string_concatenate.4 = call i8* @_string_concatenate(i8* %call__string_concatenate.3, i8* %T.33)
  %T.34 = getelementptr [5 x i8], [5 x i8]* @.conststr.6, i32 0, i32 0
  %call__string_concatenate.5 = call i8* @_string_concatenate(i8* %call__string_concatenate.4, i8* %T.34)
  %T.35 = getelementptr [5 x i8], [5 x i8]* @.conststr.7, i32 0, i32 0
  %call__string_concatenate.6 = call i8* @_string_concatenate(i8* %call__string_concatenate.5, i8* %T.35)
  %T.36 = getelementptr [5 x i8], [5 x i8]* @.conststr.8, i32 0, i32 0
  %call__string_concatenate.7 = call i8* @_string_concatenate(i8* %call__string_concatenate.6, i8* %T.36)
  %T.37 = getelementptr [6 x i8], [6 x i8]* @.conststr.9, i32 0, i32 0
  %call__string_concatenate.8 = call i8* @_string_concatenate(i8* %call__string_concatenate.7, i8* %T.37)
  call void @println(i8* %call__string_concatenate.8)
  br label %for_incr

if_taken:                                         ; preds = %for_body.2
  %load_a.2 = load i32**, i32*** @a
  %T.19 = getelementptr i32*, i32** %load_a.2, i32 %i.1.0
  %load_T.4 = load i32*, i32** %T.19
  %T.20 = getelementptr i32, i32* %load_T.4, i32 0
  %load_T.5 = load i32, i32* %T.20
  %T.21 = add i32 %sum.1, %load_T.5
  br label %if_after

if_after:                                         ; preds = %if_taken, %for_body.2
  %sum.2 = phi i32 [ %T.21, %if_taken ], [ %sum.1, %for_body.2 ]
  %T.22 = and i32 %j.1, 1
  %T.23 = icmp eq i32 %T.22, 1
  br i1 %T.23, label %if_taken.1, label %if_after.1

if_taken.1:                                       ; preds = %if_after
  %load_a.3 = load i32**, i32*** @a
  %T.24 = getelementptr i32*, i32** %load_a.3, i32 %i.1.0
  %load_T.6 = load i32*, i32** %T.24
  %T.25 = getelementptr i32, i32* %load_T.6, i32 29
  %load_T.7 = load i32, i32* %T.25
  %T.26 = add i32 %sum.2, %load_T.7
  br label %if_after.1

if_after.1:                                       ; preds = %if_taken.1, %if_after
  %sum.3 = phi i32 [ %T.26, %if_taken.1 ], [ %sum.2, %if_after ]
  br label %for_incr.1

retBlock:                                         ; preds = %for_after.1
  ret i32 0
}

define void @__global_init() {
init:
  %T = mul i32 8, 30
  %T.1 = add i32 %T, 4
  %M = sext i32 %T.1 to i64
  %call_malloc = call i8* @malloc(i64 %M)
  %M.1 = bitcast i8* %call_malloc to i32*
  store i32 30, i32* %M.1
  %T.2 = getelementptr i32, i32* %M.1, i32 1
  %M.2 = bitcast i32* %T.2 to i32**
  br label %for_cond

for_cond:                                         ; preds = %for_body, %init
  %i.0 = phi i32 [ 0, %init ], [ %T.8, %for_body ]
  %T.3 = icmp slt i32 %i.0, 30
  br i1 %T.3, label %for_body, label %for_after

for_body:                                         ; preds = %for_cond
  %T.4 = getelementptr i32*, i32** %M.2, i32 %i.0
  %T.5 = mul i32 4, 30
  %T.6 = add i32 %T.5, 4
  %M.3 = sext i32 %T.6 to i64
  %call_malloc.1 = call i8* @malloc(i64 %M.3)
  %M.4 = bitcast i8* %call_malloc.1 to i32*
  store i32 30, i32* %M.4
  %T.7 = getelementptr i32, i32* %M.4, i32 1
  %M.5 = bitcast i32* %T.7 to i32*
  store i32* %M.5, i32** %T.4
  %T.8 = add i32 %i.0, 1
  br label %for_cond

for_after:                                        ; preds = %for_cond
  store i32** %M.2, i32*** @a
  %T.9 = mul i32 8, 30
  %T.10 = add i32 %T.9, 4
  %M.6 = sext i32 %T.10 to i64
  %call_malloc.2 = call i8* @malloc(i64 %M.6)
  %M.7 = bitcast i8* %call_malloc.2 to i32*
  store i32 30, i32* %M.7
  %T.11 = getelementptr i32, i32* %M.7, i32 1
  %M.8 = bitcast i32* %T.11 to i8**
  store i8** %M.8, i8*** @str
  ret void
}

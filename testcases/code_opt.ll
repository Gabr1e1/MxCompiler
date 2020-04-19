; ModuleID = './testcases/mycode.ll'
source_filename = "./testcases/mycode.ll"

%struct.EdgeList = type { %struct.Edge**, i32*, i32*, i32 }
%struct.Edge = type { i32, i32, i32 }
%struct.Heap_Node = type { %struct.Array_Node* }
%struct.Array_Node = type { %struct.Node**, i32 }
%struct.Node = type { i32, i32 }

@n.2 = global i32 0, align 8
@m.2 = global i32 0, align 8
@g = global %struct.EdgeList* null, align 8
@INF = global i32 0, align 8
@.conststr = constant [3 x i8] c"-1\00", align 1
@.conststr.1 = constant [2 x i8] c" \00", align 1
@.conststr.2 = constant [1 x i8] zeroinitializer, align 1

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

define void @init() {
func_init:
  %call_getInt = call i32 @getInt()
  store i32 %call_getInt, i32* @n.2
  %call_getInt.1 = call i32 @getInt()
  store i32 %call_getInt.1, i32* @m.2
  %call_malloc.7 = call i8* @malloc(i64 28)
  %M.22 = bitcast i8* %call_malloc.7 to %struct.EdgeList*
  call void @struct.EdgeList_EdgeList(%struct.EdgeList* %M.22)
  store %struct.EdgeList* %M.22, %struct.EdgeList** @g
  %load_g.1 = load %struct.EdgeList*, %struct.EdgeList** @g
  %load_n.3 = load i32, i32* @n.2
  %load_m.4 = load i32, i32* @m.2
  call void @struct.EdgeList_init(%struct.EdgeList* %load_g.1, i32 %load_n.3, i32 %load_m.4)
  br label %for_cond.3

for_cond.3:                                       ; preds = %for_body.3, %func_init
  %i.7.0 = phi i32 [ 0, %func_init ], [ %T.135, %for_body.3 ]
  %load_m.5 = load i32, i32* @m.2
  %T.134 = icmp slt i32 %i.7.0, %load_m.5
  br i1 %T.134, label %for_body.3, label %retBlock.28

for_body.3:                                       ; preds = %for_cond.3
  %call_getInt.2 = call i32 @getInt()
  %call_getInt.3 = call i32 @getInt()
  %call_getInt.4 = call i32 @getInt()
  %load_g.2 = load %struct.EdgeList*, %struct.EdgeList** @g
  call void @struct.EdgeList_addEdge(%struct.EdgeList* %load_g.2, i32 %call_getInt.2, i32 %call_getInt.3, i32 %call_getInt.4)
  %T.135 = add i32 %i.7.0, 1
  br label %for_cond.3

retBlock.28:                                      ; preds = %for_cond.3
  ret void
}

define i32* @dijkstra(i32 %s) {
func_init.1:
  %load_n.4 = load i32, i32* @n.2
  %T.136 = mul i32 4, %load_n.4
  %T.137 = add i32 %T.136, 4
  %M.23 = sext i32 %T.137 to i64
  %call_malloc.8 = call i8* @malloc(i64 %M.23)
  %M.24 = bitcast i8* %call_malloc.8 to i32*
  store i32 %load_n.4, i32* %M.24
  %T.138 = getelementptr i32, i32* %M.24, i32 1
  %M.25 = bitcast i32* %T.138 to i32*
  %load_n.5 = load i32, i32* @n.2
  %T.139 = mul i32 4, %load_n.5
  %T.140 = add i32 %T.139, 4
  %M.26 = sext i32 %T.140 to i64
  %call_malloc.9 = call i8* @malloc(i64 %M.26)
  %M.27 = bitcast i8* %call_malloc.9 to i32*
  store i32 %load_n.5, i32* %M.27
  %T.141 = getelementptr i32, i32* %M.27, i32 1
  %M.28 = bitcast i32* %T.141 to i32*
  br label %for_cond.4

for_cond.4:                                       ; preds = %for_body.4, %func_init.1
  %i.8.0 = phi i32 [ 0, %func_init.1 ], [ %T.145, %for_body.4 ]
  %load_n.6 = load i32, i32* @n.2
  %T.142 = icmp slt i32 %i.8.0, %load_n.6
  br i1 %T.142, label %for_body.4, label %for_after.4

for_body.4:                                       ; preds = %for_cond.4
  %T.143 = getelementptr i32, i32* %M.28, i32 %i.8.0
  %load_INF.1 = load i32, i32* @INF
  store i32 %load_INF.1, i32* %T.143
  %T.144 = getelementptr i32, i32* %M.25, i32 %i.8.0
  store i32 0, i32* %T.144
  %T.145 = add i32 %i.8.0, 1
  br label %for_cond.4

for_after.4:                                      ; preds = %for_cond.4
  %T.146 = getelementptr i32, i32* %M.28, i32 %s
  store i32 0, i32* %T.146
  %call_malloc.10 = call i8* @malloc(i64 8)
  %M.29 = bitcast i8* %call_malloc.10 to %struct.Heap_Node*
  call void @struct.Heap_Node_Heap_Node(%struct.Heap_Node* %M.29)
  %call_malloc.11 = call i8* @malloc(i64 8)
  %M.30 = bitcast i8* %call_malloc.11 to %struct.Node*
  call void @struct.Node_Node(%struct.Node* %M.30)
  %T.147 = getelementptr %struct.Node, %struct.Node* %M.30, i32 0, i32 1
  store i32 0, i32* %T.147
  %T.148 = getelementptr %struct.Node, %struct.Node* %M.30, i32 0, i32 0
  store i32 %s, i32* %T.148
  call void @struct.Heap_Node_push(%struct.Heap_Node* %M.29, %struct.Node* %M.30)
  br label %while_cond.2

while_cond.2:                                     ; preds = %for_after.5, %if_taken.5, %for_after.4
  %k.0 = phi i32 [ undef, %for_after.4 ], [ %k.0, %if_taken.5 ], [ %k.1, %for_after.5 ]
  %v.9.0 = phi i32 [ undef, %for_after.4 ], [ %v.9.0, %if_taken.5 ], [ %v.9.1, %for_after.5 ]
  %w.3.0 = phi i32 [ undef, %for_after.4 ], [ %w.3.0, %if_taken.5 ], [ %w.3.1, %for_after.5 ]
  %alt.0 = phi i32 [ undef, %for_after.4 ], [ %alt.0, %if_taken.5 ], [ %alt.1, %for_after.5 ]
  %call_struct.Heap_Node_size.4 = call i32 @struct.Heap_Node_size(%struct.Heap_Node* %M.29)
  %T.149 = icmp ne i32 %call_struct.Heap_Node_size.4, 0
  br i1 %T.149, label %while_body.2, label %retBlock.29

while_body.2:                                     ; preds = %while_cond.2
  %call_struct.Heap_Node_pop = call %struct.Node* @struct.Heap_Node_pop(%struct.Heap_Node* %M.29)
  %T.150 = getelementptr %struct.Node, %struct.Node* %call_struct.Heap_Node_pop, i32 0, i32 0
  %load_T.97 = load i32, i32* %T.150
  %T.151 = getelementptr i32, i32* %M.25, i32 %load_T.97
  %load_T.98 = load i32, i32* %T.151
  %T.152 = icmp eq i32 %load_T.98, 1
  br i1 %T.152, label %if_taken.5, label %if_after.5

if_taken.5:                                       ; preds = %while_body.2
  br label %while_cond.2

if_after.5:                                       ; preds = %while_body.2
  %T.153 = getelementptr i32, i32* %M.25, i32 %load_T.97
  store i32 1, i32* %T.153
  %load_g.3 = load %struct.EdgeList*, %struct.EdgeList** @g
  %T.154 = getelementptr %struct.EdgeList, %struct.EdgeList* %load_g.3, i32 0, i32 2
  %load_T.100 = load i32*, i32** %T.154
  %T.155 = getelementptr i32, i32* %load_T.100, i32 %load_T.97
  %load_T.101 = load i32, i32* %T.155
  br label %for_cond.5

for_cond.5:                                       ; preds = %for_incr.5, %if_after.5
  %node.1 = phi %struct.Node* [ %call_struct.Heap_Node_pop, %if_after.5 ], [ %node.2, %for_incr.5 ]
  %k.1 = phi i32 [ %load_T.101, %if_after.5 ], [ %load_T.115, %for_incr.5 ]
  %v.9.1 = phi i32 [ %v.9.0, %if_after.5 ], [ %load_T.104, %for_incr.5 ]
  %w.3.1 = phi i32 [ %w.3.0, %if_after.5 ], [ %load_T.107, %for_incr.5 ]
  %alt.1 = phi i32 [ %alt.0, %if_after.5 ], [ %T.165, %for_incr.5 ]
  %T.156 = sub i32 0, 1
  %T.157 = icmp ne i32 %k.1, %T.156
  br i1 %T.157, label %for_body.5, label %for_after.5

for_body.5:                                       ; preds = %for_cond.5
  %load_g.4 = load %struct.EdgeList*, %struct.EdgeList** @g
  %T.158 = getelementptr %struct.EdgeList, %struct.EdgeList* %load_g.4, i32 0, i32 0
  %load_T.102 = load %struct.Edge**, %struct.Edge*** %T.158
  %T.159 = getelementptr %struct.Edge*, %struct.Edge** %load_T.102, i32 %k.1
  %load_T.103 = load %struct.Edge*, %struct.Edge** %T.159
  %T.160 = getelementptr %struct.Edge, %struct.Edge* %load_T.103, i32 0, i32 1
  %load_T.104 = load i32, i32* %T.160
  %load_g.5 = load %struct.EdgeList*, %struct.EdgeList** @g
  %T.161 = getelementptr %struct.EdgeList, %struct.EdgeList* %load_g.5, i32 0, i32 0
  %load_T.105 = load %struct.Edge**, %struct.Edge*** %T.161
  %T.162 = getelementptr %struct.Edge*, %struct.Edge** %load_T.105, i32 %k.1
  %load_T.106 = load %struct.Edge*, %struct.Edge** %T.162
  %T.163 = getelementptr %struct.Edge, %struct.Edge* %load_T.106, i32 0, i32 2
  %load_T.107 = load i32, i32* %T.163
  %T.164 = getelementptr i32, i32* %M.28, i32 %load_T.97
  %load_T.108 = load i32, i32* %T.164
  %T.165 = add i32 %load_T.108, %load_T.107
  %T.166 = getelementptr i32, i32* %M.28, i32 %load_T.104
  %load_T.109 = load i32, i32* %T.166
  %T.167 = icmp sge i32 %T.165, %load_T.109
  br i1 %T.167, label %for_incr.5, label %if_after.6

for_incr.5:                                       ; preds = %for_body.5, %if_after.6
  %node.2 = phi %struct.Node* [ %M.31, %if_after.6 ], [ %node.1, %for_body.5 ]
  %load_g.6 = load %struct.EdgeList*, %struct.EdgeList** @g
  %T.172 = getelementptr %struct.EdgeList, %struct.EdgeList* %load_g.6, i32 0, i32 1
  %load_T.114 = load i32*, i32** %T.172
  %T.173 = getelementptr i32, i32* %load_T.114, i32 %k.1
  %load_T.115 = load i32, i32* %T.173
  br label %for_cond.5

for_after.5:                                      ; preds = %for_cond.5
  br label %while_cond.2

if_after.6:                                       ; preds = %for_body.5
  %T.168 = getelementptr i32, i32* %M.28, i32 %load_T.104
  store i32 %T.165, i32* %T.168
  %call_malloc.12 = call i8* @malloc(i64 8)
  %M.31 = bitcast i8* %call_malloc.12 to %struct.Node*
  call void @struct.Node_Node(%struct.Node* %M.31)
  %T.169 = getelementptr %struct.Node, %struct.Node* %M.31, i32 0, i32 0
  store i32 %load_T.104, i32* %T.169
  %T.170 = getelementptr %struct.Node, %struct.Node* %M.31, i32 0, i32 1
  %T.171 = getelementptr i32, i32* %M.28, i32 %load_T.104
  %load_T.112 = load i32, i32* %T.171
  store i32 %load_T.112, i32* %T.170
  call void @struct.Heap_Node_push(%struct.Heap_Node* %M.29, %struct.Node* %M.31)
  br label %for_incr.5

retBlock.29:                                      ; preds = %while_cond.2
  ret i32* %M.28
}

define i32 @main() {
func_init.2:
  call void @__global_init()
  call void @init()
  br label %for_cond.6

for_cond.6:                                       ; preds = %for_after.7, %func_init.2
  %i.9.0 = phi i32 [ 0, %func_init.2 ], [ %T.183, %for_after.7 ]
  %load_n.7 = load i32, i32* @n.2
  %T.174 = icmp slt i32 %i.9.0, %load_n.7
  br i1 %T.174, label %for_body.6, label %retBlock.30

for_body.6:                                       ; preds = %for_cond.6
  %call_dijkstra = call i32* @dijkstra(i32 %i.9.0)
  br label %for_cond.7

for_cond.7:                                       ; preds = %if_after.7, %for_body.6
  %j.2.1 = phi i32 [ 0, %for_body.6 ], [ %T.181, %if_after.7 ]
  %load_n.8 = load i32, i32* @n.2
  %T.175 = icmp slt i32 %j.2.1, %load_n.8
  br i1 %T.175, label %for_body.7, label %for_after.7

for_body.7:                                       ; preds = %for_cond.7
  %T.176 = getelementptr i32, i32* %call_dijkstra, i32 %j.2.1
  %load_T.116 = load i32, i32* %T.176
  %load_INF.2 = load i32, i32* @INF
  %T.177 = icmp eq i32 %load_T.116, %load_INF.2
  br i1 %T.177, label %if_taken.7, label %if_notTaken

for_after.7:                                      ; preds = %for_cond.7
  %T.182 = getelementptr [1 x i8], [1 x i8]* @.conststr.2, i32 0, i32 0
  call void @println(i8* %T.182)
  %T.183 = add i32 %i.9.0, 1
  br label %for_cond.6

if_taken.7:                                       ; preds = %for_body.7
  %T.178 = getelementptr [3 x i8], [3 x i8]* @.conststr, i32 0, i32 0
  call void @print(i8* %T.178)
  br label %if_after.7

if_notTaken:                                      ; preds = %for_body.7
  %T.179 = getelementptr i32, i32* %call_dijkstra, i32 %j.2.1
  %load_T.117 = load i32, i32* %T.179
  %call_toString = call i8* @toString(i32 %load_T.117)
  call void @print(i8* %call_toString)
  br label %if_after.7

if_after.7:                                       ; preds = %if_notTaken, %if_taken.7
  %T.180 = getelementptr [2 x i8], [2 x i8]* @.conststr.1, i32 0, i32 0
  call void @print(i8* %T.180)
  %T.181 = add i32 %j.2.1, 1
  br label %for_cond.7

retBlock.30:                                      ; preds = %for_cond.6
  ret i32 0
}

define void @struct.Edge_Edge(%struct.Edge* %this) {
retBlock:
  ret void
}

define void @struct.EdgeList_init(%struct.EdgeList* %this.1, i32 %n, i32 %m) {
func_init.4:
  %T = getelementptr %struct.EdgeList, %struct.EdgeList* %this.1, i32 0, i32 0
  %T.1 = mul i32 8, %m
  %T.2 = add i32 %T.1, 4
  %M = sext i32 %T.2 to i64
  %call_malloc = call i8* @malloc(i64 %M)
  %M.1 = bitcast i8* %call_malloc to i32*
  store i32 %m, i32* %M.1
  %T.3 = getelementptr i32, i32* %M.1, i32 1
  %M.2 = bitcast i32* %T.3 to %struct.Edge**
  store %struct.Edge** %M.2, %struct.Edge*** %T
  %T.4 = getelementptr %struct.EdgeList, %struct.EdgeList* %this.1, i32 0, i32 1
  %T.5 = mul i32 4, %m
  %T.6 = add i32 %T.5, 4
  %M.3 = sext i32 %T.6 to i64
  %call_malloc.1 = call i8* @malloc(i64 %M.3)
  %M.4 = bitcast i8* %call_malloc.1 to i32*
  store i32 %m, i32* %M.4
  %T.7 = getelementptr i32, i32* %M.4, i32 1
  %M.5 = bitcast i32* %T.7 to i32*
  store i32* %M.5, i32** %T.4
  %T.8 = getelementptr %struct.EdgeList, %struct.EdgeList* %this.1, i32 0, i32 2
  %T.9 = mul i32 4, %n
  %T.10 = add i32 %T.9, 4
  %M.6 = sext i32 %T.10 to i64
  %call_malloc.2 = call i8* @malloc(i64 %M.6)
  %M.7 = bitcast i8* %call_malloc.2 to i32*
  store i32 %n, i32* %M.7
  %T.11 = getelementptr i32, i32* %M.7, i32 1
  %M.8 = bitcast i32* %T.11 to i32*
  store i32* %M.8, i32** %T.8
  br label %for_cond

for_cond:                                         ; preds = %for_body, %func_init.4
  %i.6.0 = phi i32 [ 0, %func_init.4 ], [ %T.16, %for_body ]
  %T.12 = icmp slt i32 %i.6.0, %m
  br i1 %T.12, label %for_body, label %for_after

for_body:                                         ; preds = %for_cond
  %T.13 = getelementptr %struct.EdgeList, %struct.EdgeList* %this.1, i32 0, i32 1
  %load_T.3 = load i32*, i32** %T.13
  %T.14 = getelementptr i32, i32* %load_T.3, i32 %i.6.0
  %T.15 = sub i32 0, 1
  store i32 %T.15, i32* %T.14
  %T.16 = add i32 %i.6.0, 1
  br label %for_cond

for_after:                                        ; preds = %for_cond
  br label %for_cond.1

for_cond.1:                                       ; preds = %for_body.1, %for_after
  %i.6.1 = phi i32 [ 0, %for_after ], [ %T.21, %for_body.1 ]
  %T.17 = icmp slt i32 %i.6.1, %n
  br i1 %T.17, label %for_body.1, label %for_after.1

for_body.1:                                       ; preds = %for_cond.1
  %T.18 = getelementptr %struct.EdgeList, %struct.EdgeList* %this.1, i32 0, i32 2
  %load_T.5 = load i32*, i32** %T.18
  %T.19 = getelementptr i32, i32* %load_T.5, i32 %i.6.1
  %T.20 = sub i32 0, 1
  store i32 %T.20, i32* %T.19
  %T.21 = add i32 %i.6.1, 1
  br label %for_cond.1

for_after.1:                                      ; preds = %for_cond.1
  %T.22 = getelementptr %struct.EdgeList, %struct.EdgeList* %this.1, i32 0, i32 3
  store i32 0, i32* %T.22
  ret void
}

define void @struct.EdgeList_addEdge(%struct.EdgeList* %this.2, i32 %u, i32 %v, i32 %w) {
func_init.5:
  %call_malloc.3 = call i8* @malloc(i64 12)
  %M.9 = bitcast i8* %call_malloc.3 to %struct.Edge*
  call void @struct.Edge_Edge(%struct.Edge* %M.9)
  %T.23 = getelementptr %struct.Edge, %struct.Edge* %M.9, i32 0, i32 0
  store i32 %u, i32* %T.23
  %T.24 = getelementptr %struct.Edge, %struct.Edge* %M.9, i32 0, i32 1
  store i32 %v, i32* %T.24
  %T.25 = getelementptr %struct.Edge, %struct.Edge* %M.9, i32 0, i32 2
  store i32 %w, i32* %T.25
  %T.26 = getelementptr %struct.EdgeList, %struct.EdgeList* %this.2, i32 0, i32 0
  %load_T.11 = load %struct.Edge**, %struct.Edge*** %T.26
  %T.27 = getelementptr %struct.EdgeList, %struct.EdgeList* %this.2, i32 0, i32 3
  %load_T.12 = load i32, i32* %T.27
  %T.28 = getelementptr %struct.Edge*, %struct.Edge** %load_T.11, i32 %load_T.12
  store %struct.Edge* %M.9, %struct.Edge** %T.28
  %T.29 = getelementptr %struct.EdgeList, %struct.EdgeList* %this.2, i32 0, i32 1
  %load_T.14 = load i32*, i32** %T.29
  %T.30 = getelementptr %struct.EdgeList, %struct.EdgeList* %this.2, i32 0, i32 3
  %load_T.15 = load i32, i32* %T.30
  %T.31 = getelementptr i32, i32* %load_T.14, i32 %load_T.15
  %T.32 = getelementptr %struct.EdgeList, %struct.EdgeList* %this.2, i32 0, i32 2
  %load_T.16 = load i32*, i32** %T.32
  %T.33 = getelementptr i32, i32* %load_T.16, i32 %u
  %load_T.17 = load i32, i32* %T.33
  store i32 %load_T.17, i32* %T.31
  %T.34 = getelementptr %struct.EdgeList, %struct.EdgeList* %this.2, i32 0, i32 2
  %load_T.19 = load i32*, i32** %T.34
  %T.35 = getelementptr i32, i32* %load_T.19, i32 %u
  %T.36 = getelementptr %struct.EdgeList, %struct.EdgeList* %this.2, i32 0, i32 3
  %load_T.20 = load i32, i32* %T.36
  store i32 %load_T.20, i32* %T.35
  %T.37 = getelementptr %struct.EdgeList, %struct.EdgeList* %this.2, i32 0, i32 3
  %load_T.22 = load i32, i32* %T.37
  %T.38 = add i32 %load_T.22, 1
  store i32 %T.38, i32* %T.37
  ret void
}

define i32 @struct.EdgeList_nVertices(%struct.EdgeList* %this.3) {
func_init.6:
  %T.39 = getelementptr %struct.EdgeList, %struct.EdgeList* %this.3, i32 0, i32 2
  %load_T.24 = load i32*, i32** %T.39
  %M.10 = bitcast i32* %load_T.24 to i32*
  %T.40 = getelementptr i32, i32* %M.10, i32 -1
  %load_T.25 = load i32, i32* %T.40
  ret i32 %load_T.25
}

define i32 @struct.EdgeList_nEdges(%struct.EdgeList* %this.4) {
func_init.7:
  %T.41 = getelementptr %struct.EdgeList, %struct.EdgeList* %this.4, i32 0, i32 0
  %load_T.26 = load %struct.Edge**, %struct.Edge*** %T.41
  %M.11 = bitcast %struct.Edge** %load_T.26 to i32*
  %T.42 = getelementptr i32, i32* %M.11, i32 -1
  %load_T.27 = load i32, i32* %T.42
  ret i32 %load_T.27
}

define void @struct.EdgeList_EdgeList(%struct.EdgeList* %this.5) {
retBlock.5:
  ret void
}

define void @struct.Array_Node_push_back(%struct.Array_Node* %this.6, %struct.Node* %v.2) {
func_init.9:
  %call_struct.Array_Node_size = call i32 @struct.Array_Node_size(%struct.Array_Node* %this.6)
  %T.43 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.6, i32 0, i32 0
  %load_T.28 = load %struct.Node**, %struct.Node*** %T.43
  %M.12 = bitcast %struct.Node** %load_T.28 to i32*
  %T.44 = getelementptr i32, i32* %M.12, i32 -1
  %load_T.29 = load i32, i32* %T.44
  %T.45 = icmp eq i32 %call_struct.Array_Node_size, %load_T.29
  br i1 %T.45, label %if_taken, label %if_after

if_taken:                                         ; preds = %func_init.9
  call void @struct.Array_Node_doubleStorage(%struct.Array_Node* %this.6)
  br label %if_after

if_after:                                         ; preds = %if_taken, %func_init.9
  %T.46 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.6, i32 0, i32 0
  %load_T.30 = load %struct.Node**, %struct.Node*** %T.46
  %T.47 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.6, i32 0, i32 1
  %load_T.31 = load i32, i32* %T.47
  %T.48 = getelementptr %struct.Node*, %struct.Node** %load_T.30, i32 %load_T.31
  store %struct.Node* %v.2, %struct.Node** %T.48
  %T.49 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.6, i32 0, i32 1
  %load_T.33 = load i32, i32* %T.49
  %T.50 = add i32 %load_T.33, 1
  store i32 %T.50, i32* %T.49
  ret void
}

define %struct.Node* @struct.Array_Node_pop_back(%struct.Array_Node* %this.7) {
func_init.10:
  %T.51 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.7, i32 0, i32 1
  %load_T.35 = load i32, i32* %T.51
  %T.52 = sub i32 %load_T.35, 1
  store i32 %T.52, i32* %T.51
  %T.53 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.7, i32 0, i32 0
  %load_T.37 = load %struct.Node**, %struct.Node*** %T.53
  %T.54 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.7, i32 0, i32 1
  %load_T.38 = load i32, i32* %T.54
  %T.55 = getelementptr %struct.Node*, %struct.Node** %load_T.37, i32 %load_T.38
  %load_T.39 = load %struct.Node*, %struct.Node** %T.55
  ret %struct.Node* %load_T.39
}

define %struct.Node* @struct.Array_Node_back(%struct.Array_Node* %this.8) {
func_init.11:
  %T.56 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.8, i32 0, i32 0
  %load_T.40 = load %struct.Node**, %struct.Node*** %T.56
  %T.57 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.8, i32 0, i32 1
  %load_T.41 = load i32, i32* %T.57
  %T.58 = sub i32 %load_T.41, 1
  %T.59 = getelementptr %struct.Node*, %struct.Node** %load_T.40, i32 %T.58
  %load_T.42 = load %struct.Node*, %struct.Node** %T.59
  ret %struct.Node* %load_T.42
}

define %struct.Node* @struct.Array_Node_front(%struct.Array_Node* %this.9) {
func_init.12:
  %T.60 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.9, i32 0, i32 0
  %load_T.43 = load %struct.Node**, %struct.Node*** %T.60
  %T.61 = getelementptr %struct.Node*, %struct.Node** %load_T.43, i32 0
  %load_T.44 = load %struct.Node*, %struct.Node** %T.61
  ret %struct.Node* %load_T.44
}

define i32 @struct.Array_Node_size(%struct.Array_Node* %this.10) {
func_init.13:
  %T.62 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.10, i32 0, i32 1
  %load_T.45 = load i32, i32* %T.62
  ret i32 %load_T.45
}

define void @struct.Array_Node_resize(%struct.Array_Node* %this.11, i32 %newSize) {
func_init.14:
  br label %while_cond

while_cond:                                       ; preds = %while_body, %func_init.14
  %T.63 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.11, i32 0, i32 0
  %load_T.46 = load %struct.Node**, %struct.Node*** %T.63
  %M.13 = bitcast %struct.Node** %load_T.46 to i32*
  %T.64 = getelementptr i32, i32* %M.13, i32 -1
  %load_T.47 = load i32, i32* %T.64
  %T.65 = icmp slt i32 %load_T.47, %newSize
  br i1 %T.65, label %while_body, label %while_after

while_body:                                       ; preds = %while_cond
  call void @struct.Array_Node_doubleStorage(%struct.Array_Node* %this.11)
  br label %while_cond

while_after:                                      ; preds = %while_cond
  %T.66 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.11, i32 0, i32 1
  store i32 %newSize, i32* %T.66
  ret void
}

define %struct.Node* @struct.Array_Node_get(%struct.Array_Node* %this.12, i32 %i) {
func_init.15:
  %T.67 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.12, i32 0, i32 0
  %load_T.49 = load %struct.Node**, %struct.Node*** %T.67
  %T.68 = getelementptr %struct.Node*, %struct.Node** %load_T.49, i32 %i
  %load_T.50 = load %struct.Node*, %struct.Node** %T.68
  ret %struct.Node* %load_T.50
}

define void @struct.Array_Node_set(%struct.Array_Node* %this.13, i32 %i.2, %struct.Node* %v.4) {
func_init.16:
  %T.69 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.13, i32 0, i32 0
  %load_T.51 = load %struct.Node**, %struct.Node*** %T.69
  %T.70 = getelementptr %struct.Node*, %struct.Node** %load_T.51, i32 %i.2
  store %struct.Node* %v.4, %struct.Node** %T.70
  ret void
}

define void @struct.Array_Node_swap(%struct.Array_Node* %this.14, i32 %i.4, i32 %j) {
func_init.17:
  %T.71 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.14, i32 0, i32 0
  %load_T.53 = load %struct.Node**, %struct.Node*** %T.71
  %T.72 = getelementptr %struct.Node*, %struct.Node** %load_T.53, i32 %i.4
  %load_T.54 = load %struct.Node*, %struct.Node** %T.72
  %T.73 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.14, i32 0, i32 0
  %load_T.55 = load %struct.Node**, %struct.Node*** %T.73
  %T.74 = getelementptr %struct.Node*, %struct.Node** %load_T.55, i32 %i.4
  %T.75 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.14, i32 0, i32 0
  %load_T.56 = load %struct.Node**, %struct.Node*** %T.75
  %T.76 = getelementptr %struct.Node*, %struct.Node** %load_T.56, i32 %j
  %load_T.57 = load %struct.Node*, %struct.Node** %T.76
  store %struct.Node* %load_T.57, %struct.Node** %T.74
  %T.77 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.14, i32 0, i32 0
  %load_T.59 = load %struct.Node**, %struct.Node*** %T.77
  %T.78 = getelementptr %struct.Node*, %struct.Node** %load_T.59, i32 %j
  store %struct.Node* %load_T.54, %struct.Node** %T.78
  ret void
}

define void @struct.Array_Node_doubleStorage(%struct.Array_Node* %this.15) {
func_init.18:
  %T.79 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.15, i32 0, i32 0
  %load_T.61 = load %struct.Node**, %struct.Node*** %T.79
  %T.80 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.15, i32 0, i32 1
  %load_T.62 = load i32, i32* %T.80
  %T.81 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.15, i32 0, i32 0
  %M.14 = bitcast %struct.Node** %load_T.61 to i32*
  %T.82 = getelementptr i32, i32* %M.14, i32 -1
  %load_T.63 = load i32, i32* %T.82
  %T.83 = mul i32 %load_T.63, 2
  %T.84 = mul i32 8, %T.83
  %T.85 = add i32 %T.84, 4
  %M.15 = sext i32 %T.85 to i64
  %call_malloc.4 = call i8* @malloc(i64 %M.15)
  %M.16 = bitcast i8* %call_malloc.4 to i32*
  store i32 %T.83, i32* %M.16
  %T.86 = getelementptr i32, i32* %M.16, i32 1
  %M.17 = bitcast i32* %T.86 to %struct.Node**
  store %struct.Node** %M.17, %struct.Node*** %T.81
  %T.87 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.15, i32 0, i32 1
  store i32 0, i32* %T.87
  br label %for_cond.2

for_cond.2:                                       ; preds = %for_body.2, %func_init.18
  %T.88 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.15, i32 0, i32 1
  %load_T.66 = load i32, i32* %T.88
  %T.89 = icmp ne i32 %load_T.66, %load_T.62
  br i1 %T.89, label %for_body.2, label %retBlock.15

for_body.2:                                       ; preds = %for_cond.2
  %T.90 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.15, i32 0, i32 0
  %load_T.67 = load %struct.Node**, %struct.Node*** %T.90
  %T.91 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.15, i32 0, i32 1
  %load_T.68 = load i32, i32* %T.91
  %T.92 = getelementptr %struct.Node*, %struct.Node** %load_T.67, i32 %load_T.68
  %T.93 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.15, i32 0, i32 1
  %load_T.69 = load i32, i32* %T.93
  %T.94 = getelementptr %struct.Node*, %struct.Node** %load_T.61, i32 %load_T.69
  %load_T.70 = load %struct.Node*, %struct.Node** %T.94
  store %struct.Node* %load_T.70, %struct.Node** %T.92
  %T.95 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.15, i32 0, i32 1
  %load_T.72 = load i32, i32* %T.95
  %T.96 = add i32 %load_T.72, 1
  store i32 %T.96, i32* %T.95
  br label %for_cond.2

retBlock.15:                                      ; preds = %for_cond.2
  ret void
}

define void @struct.Array_Node_Array_Node(%struct.Array_Node* %this.16) {
func_init.19:
  %T.97 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.16, i32 0, i32 1
  store i32 0, i32* %T.97
  %T.98 = getelementptr %struct.Array_Node, %struct.Array_Node* %this.16, i32 0, i32 0
  %T.99 = mul i32 8, 16
  %T.100 = add i32 %T.99, 4
  %M.18 = sext i32 %T.100 to i64
  %call_malloc.5 = call i8* @malloc(i64 %M.18)
  %M.19 = bitcast i8* %call_malloc.5 to i32*
  store i32 16, i32* %M.19
  %T.101 = getelementptr i32, i32* %M.19, i32 1
  %M.20 = bitcast i32* %T.101 to %struct.Node**
  store %struct.Node** %M.20, %struct.Node*** %T.98
  ret void
}

define void @struct.Heap_Node_push(%struct.Heap_Node* %this.17, %struct.Node* %v.6) {
func_init.20:
  %T.102 = getelementptr %struct.Heap_Node, %struct.Heap_Node* %this.17, i32 0, i32 0
  %load_T.76 = load %struct.Array_Node*, %struct.Array_Node** %T.102
  call void @struct.Array_Node_push_back(%struct.Array_Node* %load_T.76, %struct.Node* %v.6)
  %call_struct.Heap_Node_size = call i32 @struct.Heap_Node_size(%struct.Heap_Node* %this.17)
  %T.103 = sub i32 %call_struct.Heap_Node_size, 1
  br label %while_cond.1

while_cond.1:                                     ; preds = %if_after.1, %func_init.20
  %x.8.0 = phi i32 [ %T.103, %func_init.20 ], [ %call_struct.Heap_Node_pnt, %if_after.1 ]
  %T.104 = icmp sgt i32 %x.8.0, 0
  br i1 %T.104, label %while_body.1, label %retBlock.17

while_body.1:                                     ; preds = %while_cond.1
  %call_struct.Heap_Node_pnt = call i32 @struct.Heap_Node_pnt(%struct.Heap_Node* %this.17, i32 %x.8.0)
  %T.105 = getelementptr %struct.Heap_Node, %struct.Heap_Node* %this.17, i32 0, i32 0
  %load_T.77 = load %struct.Array_Node*, %struct.Array_Node** %T.105
  %call_struct.Array_Node_get = call %struct.Node* @struct.Array_Node_get(%struct.Array_Node* %load_T.77, i32 %call_struct.Heap_Node_pnt)
  %call_struct.Node_key_ = call i32 @struct.Node_key_(%struct.Node* %call_struct.Array_Node_get)
  %T.106 = getelementptr %struct.Heap_Node, %struct.Heap_Node* %this.17, i32 0, i32 0
  %load_T.78 = load %struct.Array_Node*, %struct.Array_Node** %T.106
  %call_struct.Array_Node_get.1 = call %struct.Node* @struct.Array_Node_get(%struct.Array_Node* %load_T.78, i32 %x.8.0)
  %call_struct.Node_key_.1 = call i32 @struct.Node_key_(%struct.Node* %call_struct.Array_Node_get.1)
  %T.107 = icmp sge i32 %call_struct.Node_key_, %call_struct.Node_key_.1
  br i1 %T.107, label %retBlock.17, label %if_after.1

if_after.1:                                       ; preds = %while_body.1
  %T.108 = getelementptr %struct.Heap_Node, %struct.Heap_Node* %this.17, i32 0, i32 0
  %load_T.79 = load %struct.Array_Node*, %struct.Array_Node** %T.108
  call void @struct.Array_Node_swap(%struct.Array_Node* %load_T.79, i32 %call_struct.Heap_Node_pnt, i32 %x.8.0)
  br label %while_cond.1

retBlock.17:                                      ; preds = %while_body.1, %while_cond.1
  ret void
}

define %struct.Node* @struct.Heap_Node_pop(%struct.Heap_Node* %this.18) {
func_init.21:
  %T.109 = getelementptr %struct.Heap_Node, %struct.Heap_Node* %this.18, i32 0, i32 0
  %load_T.80 = load %struct.Array_Node*, %struct.Array_Node** %T.109
  %call_struct.Array_Node_front = call %struct.Node* @struct.Array_Node_front(%struct.Array_Node* %load_T.80)
  %T.110 = getelementptr %struct.Heap_Node, %struct.Heap_Node* %this.18, i32 0, i32 0
  %call_struct.Heap_Node_size.1 = call i32 @struct.Heap_Node_size(%struct.Heap_Node* %this.18)
  %T.111 = sub i32 %call_struct.Heap_Node_size.1, 1
  %load_T.81 = load %struct.Array_Node*, %struct.Array_Node** %T.110
  call void @struct.Array_Node_swap(%struct.Array_Node* %load_T.81, i32 0, i32 %T.111)
  %T.112 = getelementptr %struct.Heap_Node, %struct.Heap_Node* %this.18, i32 0, i32 0
  %load_T.82 = load %struct.Array_Node*, %struct.Array_Node** %T.112
  %call_struct.Array_Node_pop_back = call %struct.Node* @struct.Array_Node_pop_back(%struct.Array_Node* %load_T.82)
  call void @struct.Heap_Node_maxHeapify(%struct.Heap_Node* %this.18, i32 0)
  ret %struct.Node* %call_struct.Array_Node_front
}

define %struct.Node* @struct.Heap_Node_top(%struct.Heap_Node* %this.19) {
func_init.22:
  %T.113 = getelementptr %struct.Heap_Node, %struct.Heap_Node* %this.19, i32 0, i32 0
  %load_T.83 = load %struct.Array_Node*, %struct.Array_Node** %T.113
  %call_struct.Array_Node_front.1 = call %struct.Node* @struct.Array_Node_front(%struct.Array_Node* %load_T.83)
  ret %struct.Node* %call_struct.Array_Node_front.1
}

define i32 @struct.Heap_Node_size(%struct.Heap_Node* %this.20) {
func_init.23:
  %T.114 = getelementptr %struct.Heap_Node, %struct.Heap_Node* %this.20, i32 0, i32 0
  %load_T.84 = load %struct.Array_Node*, %struct.Array_Node** %T.114
  %call_struct.Array_Node_size.1 = call i32 @struct.Array_Node_size(%struct.Array_Node* %load_T.84)
  ret i32 %call_struct.Array_Node_size.1
}

define i32 @struct.Heap_Node_lchild(%struct.Heap_Node* %this.21, i32 %x) {
func_init.24:
  %T.115 = mul i32 %x, 2
  %T.116 = add i32 %T.115, 1
  ret i32 %T.116
}

define i32 @struct.Heap_Node_rchild(%struct.Heap_Node* %this.22, i32 %x.2) {
func_init.25:
  %T.117 = mul i32 %x.2, 2
  %T.118 = add i32 %T.117, 2
  ret i32 %T.118
}

define i32 @struct.Heap_Node_pnt(%struct.Heap_Node* %this.23, i32 %x.4) {
func_init.26:
  %T.119 = sub i32 %x.4, 1
  %T.120 = sdiv i32 %T.119, 2
  ret i32 %T.120
}

define void @struct.Heap_Node_maxHeapify(%struct.Heap_Node* %this.24, i32 %x.6) {
func_init.27:
  %call_struct.Heap_Node_lchild = call i32 @struct.Heap_Node_lchild(%struct.Heap_Node* %this.24, i32 %x.6)
  %call_struct.Heap_Node_rchild = call i32 @struct.Heap_Node_rchild(%struct.Heap_Node* %this.24, i32 %x.6)
  %call_struct.Heap_Node_size.2 = call i32 @struct.Heap_Node_size(%struct.Heap_Node* %this.24)
  %T.121 = icmp slt i32 %call_struct.Heap_Node_lchild, %call_struct.Heap_Node_size.2
  br i1 %T.121, label %continue, label %short_circuit

continue:                                         ; preds = %func_init.27
  %T.122 = getelementptr %struct.Heap_Node, %struct.Heap_Node* %this.24, i32 0, i32 0
  %load_T.85 = load %struct.Array_Node*, %struct.Array_Node** %T.122
  %call_struct.Array_Node_get.2 = call %struct.Node* @struct.Array_Node_get(%struct.Array_Node* %load_T.85, i32 %call_struct.Heap_Node_lchild)
  %call_struct.Node_key_.2 = call i32 @struct.Node_key_(%struct.Node* %call_struct.Array_Node_get.2)
  %T.123 = getelementptr %struct.Heap_Node, %struct.Heap_Node* %this.24, i32 0, i32 0
  %load_T.86 = load %struct.Array_Node*, %struct.Array_Node** %T.123
  %call_struct.Array_Node_get.3 = call %struct.Node* @struct.Array_Node_get(%struct.Array_Node* %load_T.86, i32 %x.6)
  %call_struct.Node_key_.3 = call i32 @struct.Node_key_(%struct.Node* %call_struct.Array_Node_get.3)
  %T.124 = icmp sgt i32 %call_struct.Node_key_.2, %call_struct.Node_key_.3
  br label %short_circuit

short_circuit:                                    ; preds = %continue, %func_init.27
  %.155.0 = phi i1 [ %T.124, %continue ], [ %T.121, %func_init.27 ]
  %spec.select = select i1 %.155.0, i32 %call_struct.Heap_Node_lchild, i32 %x.6
  %call_struct.Heap_Node_size.3 = call i32 @struct.Heap_Node_size(%struct.Heap_Node* %this.24)
  %T.125 = icmp slt i32 %call_struct.Heap_Node_rchild, %call_struct.Heap_Node_size.3
  br i1 %T.125, label %continue.1, label %short_circuit.1

continue.1:                                       ; preds = %short_circuit
  %T.126 = getelementptr %struct.Heap_Node, %struct.Heap_Node* %this.24, i32 0, i32 0
  %load_T.87 = load %struct.Array_Node*, %struct.Array_Node** %T.126
  %call_struct.Array_Node_get.4 = call %struct.Node* @struct.Array_Node_get(%struct.Array_Node* %load_T.87, i32 %call_struct.Heap_Node_rchild)
  %call_struct.Node_key_.4 = call i32 @struct.Node_key_(%struct.Node* %call_struct.Array_Node_get.4)
  %T.127 = getelementptr %struct.Heap_Node, %struct.Heap_Node* %this.24, i32 0, i32 0
  %load_T.88 = load %struct.Array_Node*, %struct.Array_Node** %T.127
  %call_struct.Array_Node_get.5 = call %struct.Node* @struct.Array_Node_get(%struct.Array_Node* %load_T.88, i32 %spec.select)
  %call_struct.Node_key_.5 = call i32 @struct.Node_key_(%struct.Node* %call_struct.Array_Node_get.5)
  %T.128 = icmp sgt i32 %call_struct.Node_key_.4, %call_struct.Node_key_.5
  br label %short_circuit.1

short_circuit.1:                                  ; preds = %continue.1, %short_circuit
  %.164.0 = phi i1 [ %T.128, %continue.1 ], [ %T.125, %short_circuit ]
  %spec.select1 = select i1 %.164.0, i32 %call_struct.Heap_Node_rchild, i32 %spec.select
  %T.129 = icmp eq i32 %spec.select1, %x.6
  br i1 %T.129, label %retBlock.24, label %if_after.4

if_after.4:                                       ; preds = %short_circuit.1
  %T.130 = getelementptr %struct.Heap_Node, %struct.Heap_Node* %this.24, i32 0, i32 0
  %load_T.89 = load %struct.Array_Node*, %struct.Array_Node** %T.130
  call void @struct.Array_Node_swap(%struct.Array_Node* %load_T.89, i32 %x.6, i32 %spec.select1)
  call void @struct.Heap_Node_maxHeapify(%struct.Heap_Node* %this.24, i32 %spec.select1)
  br label %retBlock.24

retBlock.24:                                      ; preds = %short_circuit.1, %if_after.4
  ret void
}

define void @struct.Heap_Node_Heap_Node(%struct.Heap_Node* %this.25) {
func_init.28:
  %T.131 = getelementptr %struct.Heap_Node, %struct.Heap_Node* %this.25, i32 0, i32 0
  %call_malloc.6 = call i8* @malloc(i64 12)
  %M.21 = bitcast i8* %call_malloc.6 to %struct.Array_Node*
  call void @struct.Array_Node_Array_Node(%struct.Array_Node* %M.21)
  store %struct.Array_Node* %M.21, %struct.Array_Node** %T.131
  ret void
}

define i32 @struct.Node_key_(%struct.Node* %this.26) {
func_init.29:
  %T.132 = getelementptr %struct.Node, %struct.Node* %this.26, i32 0, i32 1
  %load_T.91 = load i32, i32* %T.132
  %T.133 = sub i32 0, %load_T.91
  ret i32 %T.133
}

define void @struct.Node_Node(%struct.Node* %this.27) {
retBlock.27:
  ret void
}

define void @__global_init() {
init.1:
  store i32 10000000, i32* @INF
  ret void
}

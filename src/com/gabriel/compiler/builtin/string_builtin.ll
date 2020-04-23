; ModuleID = 'string_builtin.c'
source_filename = "string_builtin.c"
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-pc-linux-gnu"

; Function Attrs: nounwind readonly uwtable
define i32 @_string_length(i8* nocapture readonly) local_unnamed_addr #0 {
  %2 = tail call i64 @strlen(i8* %0) #6
  %3 = trunc i64 %2 to i32
  ret i32 %3
}

; Function Attrs: argmemonly nounwind readonly
declare i64 @strlen(i8* nocapture) local_unnamed_addr #1

; Function Attrs: nounwind uwtable
define noalias i8* @_string_substring(i8* nocapture readonly, i32, i32) local_unnamed_addr #2 {
  %4 = sub nsw i32 %2, %1
  %5 = add nsw i32 %4, 1
  %6 = sext i32 %5 to i64
  %7 = tail call noalias i8* @malloc(i64 %6) #7
  %8 = icmp sgt i32 %2, %1
  br i1 %8, label %9, label %16

; <label>:9:                                      ; preds = %3
  %10 = sext i32 %1 to i64
  %11 = getelementptr i8, i8* %0, i64 %10
  %12 = add i32 %2, -1
  %13 = sub i32 %12, %1
  %14 = zext i32 %13 to i64
  %15 = add nuw nsw i64 %14, 1
  call void @llvm.memcpy.p0i8.p0i8.i64(i8* %7, i8* %11, i64 %15, i32 1, i1 false)
  br label %16

; <label>:16:                                     ; preds = %9, %3
  %17 = sext i32 %4 to i64
  %18 = getelementptr inbounds i8, i8* %7, i64 %17
  store i8 0, i8* %18, align 1, !tbaa !2
  ret i8* %7
}

; Function Attrs: nounwind
declare noalias i8* @malloc(i64) local_unnamed_addr #3

; Function Attrs: norecurse nounwind readonly uwtable
define i32 @_string_parseInt(i8* nocapture readonly) local_unnamed_addr #4 {
  %2 = load i8, i8* %0, align 1, !tbaa !2
  %3 = icmp eq i8 %2, 45
  %4 = select i1 %3, i32 -1, i32 1
  %5 = zext i1 %3 to i64
  %6 = getelementptr inbounds i8, i8* %0, i64 %5
  %7 = load i8, i8* %6, align 1, !tbaa !2
  %8 = add i8 %7, -48
  %9 = icmp ult i8 %8, 10
  br i1 %9, label %10, label %24

; <label>:10:                                     ; preds = %1
  br label %11

; <label>:11:                                     ; preds = %10, %11
  %12 = phi i64 [ %16, %11 ], [ %5, %10 ]
  %13 = phi i8 [ %21, %11 ], [ %7, %10 ]
  %14 = phi i32 [ %19, %11 ], [ 0, %10 ]
  %15 = mul nsw i32 %14, 10
  %16 = add nuw i64 %12, 1
  %17 = sext i8 %13 to i32
  %18 = add i32 %15, -48
  %19 = add i32 %18, %17
  %20 = getelementptr inbounds i8, i8* %0, i64 %16
  %21 = load i8, i8* %20, align 1, !tbaa !2
  %22 = add i8 %21, -48
  %23 = icmp ult i8 %22, 10
  br i1 %23, label %11, label %24

; <label>:24:                                     ; preds = %11, %1
  %25 = phi i32 [ 0, %1 ], [ %19, %11 ]
  %26 = mul nsw i32 %25, %4
  ret i32 %26
}

; Function Attrs: norecurse nounwind readonly uwtable
define i32 @_string_ord(i8* nocapture readonly, i32) local_unnamed_addr #4 {
  %3 = sext i32 %1 to i64
  %4 = getelementptr inbounds i8, i8* %0, i64 %3
  %5 = load i8, i8* %4, align 1, !tbaa !2
  %6 = sext i8 %5 to i32
  ret i32 %6
}

; Function Attrs: argmemonly nounwind
declare void @llvm.memcpy.p0i8.p0i8.i64(i8* nocapture writeonly, i8* nocapture readonly, i64, i32, i1) #5

attributes #0 = { nounwind readonly uwtable "correctly-rounded-divide-sqrt-fp-math"="false" "disable-tail-calls"="false" "less-precise-fpmad"="false" "no-frame-pointer-elim"="false" "no-infs-fp-math"="false" "no-jump-tables"="false" "no-nans-fp-math"="false" "no-signed-zeros-fp-math"="false" "no-trapping-math"="false" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+fxsr,+mmx,+sse,+sse2,+x87" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #1 = { argmemonly nounwind readonly "correctly-rounded-divide-sqrt-fp-math"="false" "disable-tail-calls"="false" "less-precise-fpmad"="false" "no-frame-pointer-elim"="false" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "no-signed-zeros-fp-math"="false" "no-trapping-math"="false" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+fxsr,+mmx,+sse,+sse2,+x87" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #2 = { nounwind uwtable "correctly-rounded-divide-sqrt-fp-math"="false" "disable-tail-calls"="false" "less-precise-fpmad"="false" "no-frame-pointer-elim"="false" "no-infs-fp-math"="false" "no-jump-tables"="false" "no-nans-fp-math"="false" "no-signed-zeros-fp-math"="false" "no-trapping-math"="false" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+fxsr,+mmx,+sse,+sse2,+x87" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #3 = { nounwind "correctly-rounded-divide-sqrt-fp-math"="false" "disable-tail-calls"="false" "less-precise-fpmad"="false" "no-frame-pointer-elim"="false" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "no-signed-zeros-fp-math"="false" "no-trapping-math"="false" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+fxsr,+mmx,+sse,+sse2,+x87" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #4 = { norecurse nounwind readonly uwtable "correctly-rounded-divide-sqrt-fp-math"="false" "disable-tail-calls"="false" "less-precise-fpmad"="false" "no-frame-pointer-elim"="false" "no-infs-fp-math"="false" "no-jump-tables"="false" "no-nans-fp-math"="false" "no-signed-zeros-fp-math"="false" "no-trapping-math"="false" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+fxsr,+mmx,+sse,+sse2,+x87" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #5 = { argmemonly nounwind }
attributes #6 = { nounwind readonly }
attributes #7 = { nounwind }

!llvm.module.flags = !{!0}
!llvm.ident = !{!1}

!0 = !{i32 1, !"wchar_size", i32 4}
!1 = !{!"clang version 6.0.0-1ubuntu2 (tags/RELEASE_600/final)"}
!2 = !{!3, !3, i64 0}
!3 = !{!"omnipotent char", !4, i64 0}
!4 = !{!"Simple C/C++ TBAA"}

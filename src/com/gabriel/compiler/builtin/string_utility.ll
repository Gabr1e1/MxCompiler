; ModuleID = 'string_utility.c'
source_filename = "string_utility.c"
target datalayout = "e-m:e-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-pc-linux-gnu"

; Function Attrs: nounwind uwtable
define noalias i8* @_string_concatenate(i8* nocapture readonly, i8* nocapture readonly) local_unnamed_addr #0 {
  %3 = tail call i64 @strlen(i8* %0) #6
  %4 = trunc i64 %3 to i32
  %5 = tail call i64 @strlen(i8* %1) #6
  %6 = trunc i64 %5 to i32
  %7 = add nsw i32 %6, %4
  %8 = add nsw i32 %7, 1
  %9 = sext i32 %8 to i64
  %10 = tail call noalias i8* @malloc(i64 %9) #7
  %11 = icmp sgt i32 %4, 0
  br i1 %11, label %12, label %14

; <label>:12:                                     ; preds = %2
  %13 = and i64 %3, 4294967295
  call void @llvm.memcpy.p0i8.p0i8.i64(i8* %10, i8* %0, i64 %13, i32 1, i1 false)
  br label %14

; <label>:14:                                     ; preds = %12, %2
  %15 = icmp sgt i32 %6, 0
  br i1 %15, label %16, label %21

; <label>:16:                                     ; preds = %14
  %17 = shl i64 %3, 32
  %18 = ashr exact i64 %17, 32
  %19 = getelementptr i8, i8* %10, i64 %18
  %20 = and i64 %5, 4294967295
  call void @llvm.memcpy.p0i8.p0i8.i64(i8* %19, i8* %1, i64 %20, i32 1, i1 false)
  br label %21

; <label>:21:                                     ; preds = %16, %14
  %22 = sext i32 %7 to i64
  %23 = getelementptr inbounds i8, i8* %10, i64 %22
  store i8 0, i8* %23, align 1, !tbaa !2
  ret i8* %10
}

; Function Attrs: argmemonly nounwind readonly
declare i64 @strlen(i8* nocapture) local_unnamed_addr #1

; Function Attrs: nounwind
declare noalias i8* @malloc(i64) local_unnamed_addr #2

; Function Attrs: nounwind readonly uwtable
define signext i8 @_string_eq(i8* nocapture readonly, i8* nocapture readonly) local_unnamed_addr #3 {
  %3 = tail call i32 @strcmp(i8* %0, i8* %1) #6
  %4 = icmp eq i32 %3, 0
  %5 = zext i1 %4 to i8
  ret i8 %5
}

; Function Attrs: nounwind readonly
declare i32 @strcmp(i8* nocapture, i8* nocapture) local_unnamed_addr #4

; Function Attrs: nounwind readonly uwtable
define signext i8 @_string_ne(i8* nocapture readonly, i8* nocapture readonly) local_unnamed_addr #3 {
  %3 = tail call i32 @strcmp(i8* %0, i8* %1) #6
  %4 = icmp ne i32 %3, 0
  %5 = zext i1 %4 to i8
  ret i8 %5
}

; Function Attrs: nounwind readonly uwtable
define signext i8 @_string_slt(i8* nocapture readonly, i8* nocapture readonly) local_unnamed_addr #3 {
  %3 = tail call i32 @strcmp(i8* %0, i8* %1) #6
  %4 = lshr i32 %3, 31
  %5 = trunc i32 %4 to i8
  ret i8 %5
}

; Function Attrs: nounwind readonly uwtable
define signext i8 @_string_sle(i8* nocapture readonly, i8* nocapture readonly) local_unnamed_addr #3 {
  %3 = tail call i32 @strcmp(i8* %0, i8* %1) #6
  %4 = icmp slt i32 %3, 1
  %5 = zext i1 %4 to i8
  ret i8 %5
}

; Function Attrs: nounwind readonly uwtable
define signext i8 @_string_sgt(i8* nocapture readonly, i8* nocapture readonly) local_unnamed_addr #3 {
  %3 = tail call i32 @strcmp(i8* %0, i8* %1) #6
  %4 = icmp sgt i32 %3, 0
  %5 = zext i1 %4 to i8
  ret i8 %5
}

; Function Attrs: nounwind readonly uwtable
define signext i8 @_string_sge(i8* nocapture readonly, i8* nocapture readonly) local_unnamed_addr #3 {
  %3 = tail call i32 @strcmp(i8* %0, i8* %1) #6
  %4 = lshr i32 %3, 31
  %5 = trunc i32 %4 to i8
  %6 = xor i8 %5, 1
  ret i8 %6
}

; Function Attrs: argmemonly nounwind
declare void @llvm.memcpy.p0i8.p0i8.i64(i8* nocapture writeonly, i8* nocapture readonly, i64, i32, i1) #5

attributes #0 = { nounwind uwtable "correctly-rounded-divide-sqrt-fp-math"="false" "disable-tail-calls"="false" "less-precise-fpmad"="false" "no-frame-pointer-elim"="false" "no-infs-fp-math"="false" "no-jump-tables"="false" "no-nans-fp-math"="false" "no-signed-zeros-fp-math"="false" "no-trapping-math"="false" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+fxsr,+mmx,+sse,+sse2,+x87" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #1 = { argmemonly nounwind readonly "correctly-rounded-divide-sqrt-fp-math"="false" "disable-tail-calls"="false" "less-precise-fpmad"="false" "no-frame-pointer-elim"="false" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "no-signed-zeros-fp-math"="false" "no-trapping-math"="false" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+fxsr,+mmx,+sse,+sse2,+x87" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #2 = { nounwind "correctly-rounded-divide-sqrt-fp-math"="false" "disable-tail-calls"="false" "less-precise-fpmad"="false" "no-frame-pointer-elim"="false" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "no-signed-zeros-fp-math"="false" "no-trapping-math"="false" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+fxsr,+mmx,+sse,+sse2,+x87" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #3 = { nounwind readonly uwtable "correctly-rounded-divide-sqrt-fp-math"="false" "disable-tail-calls"="false" "less-precise-fpmad"="false" "no-frame-pointer-elim"="false" "no-infs-fp-math"="false" "no-jump-tables"="false" "no-nans-fp-math"="false" "no-signed-zeros-fp-math"="false" "no-trapping-math"="false" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+fxsr,+mmx,+sse,+sse2,+x87" "unsafe-fp-math"="false" "use-soft-float"="false" }
attributes #4 = { nounwind readonly "correctly-rounded-divide-sqrt-fp-math"="false" "disable-tail-calls"="false" "less-precise-fpmad"="false" "no-frame-pointer-elim"="false" "no-infs-fp-math"="false" "no-nans-fp-math"="false" "no-signed-zeros-fp-math"="false" "no-trapping-math"="false" "stack-protector-buffer-size"="8" "target-cpu"="x86-64" "target-features"="+fxsr,+mmx,+sse,+sse2,+x87" "unsafe-fp-math"="false" "use-soft-float"="false" }
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

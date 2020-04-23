	.text
	.file	"llvm-link"
	.globl	print                   # -- Begin function print
	.p2align	2
	.type	print,@function
print:                                  # @print
	.cfi_startproc
# %bb.0:
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)
	.cfi_offset ra, -4
	mv	a1, a0
	lui	a0, %hi(.L.str)
	addi	a0, a0, %lo(.L.str)
	call	printf
	lw	ra, 12(sp)
	.cfi_restore ra
	addi	sp, sp, 16
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end0:
	.size	print, .Lfunc_end0-print
	.cfi_endproc
                                        # -- End function
	.globl	println                 # -- Begin function println
	.p2align	2
	.type	println,@function
println:                                # @println
	.cfi_startproc
# %bb.0:
	.cfi_def_cfa_offset 0
	tail	puts
.Lfunc_end1:
	.size	println, .Lfunc_end1-println
	.cfi_endproc
                                        # -- End function
	.globl	printInt                # -- Begin function printInt
	.p2align	2
	.type	printInt,@function
printInt:                               # @printInt
	.cfi_startproc
# %bb.0:
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)
	.cfi_offset ra, -4
	mv	a1, a0
	lui	a0, %hi(.L.str.2)
	addi	a0, a0, %lo(.L.str.2)
	call	printf
	lw	ra, 12(sp)
	.cfi_restore ra
	addi	sp, sp, 16
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end2:
	.size	printInt, .Lfunc_end2-printInt
	.cfi_endproc
                                        # -- End function
	.globl	printlnInt              # -- Begin function printlnInt
	.p2align	2
	.type	printlnInt,@function
printlnInt:                             # @printlnInt
	.cfi_startproc
# %bb.0:
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)
	.cfi_offset ra, -4
	mv	a1, a0
	lui	a0, %hi(.L.str.3)
	addi	a0, a0, %lo(.L.str.3)
	call	printf
	lw	ra, 12(sp)
	.cfi_restore ra
	addi	sp, sp, 16
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end3:
	.size	printlnInt, .Lfunc_end3-printlnInt
	.cfi_endproc
                                        # -- End function
	.globl	toString                # -- Begin function toString
	.p2align	2
	.type	toString,@function
toString:                               # @toString
	.cfi_startproc
# %bb.0:
	addi	sp, sp, -32
	.cfi_def_cfa_offset 32
	sw	ra, 28(sp)
	sw	s0, 24(sp)
	sw	s1, 20(sp)
	sw	s2, 16(sp)
	sw	s3, 12(sp)
	.cfi_offset ra, -4
	.cfi_offset s0, -8
	.cfi_offset s1, -12
	.cfi_offset s2, -16
	.cfi_offset s3, -20
	beqz	a0, .LBB4_9
# %bb.1:
	mv	s2, a0
	srai	a0, a0, 31
	add	a1, s2, a0
	xor	s3, a1, a0
	srli	s1, s2, 31
	beqz	s3, .LBB4_4
# %bb.2:
	lui	a0, 419430
	addi	a0, a0, 1639
	addi	a1, zero, 18
	mv	a2, s3
.LBB4_3:                                # =>This Inner Loop Header: Depth=1
	mulh	a3, a2, a0
	srli	a4, a3, 31
	srai	a3, a3, 2
	add	a3, a3, a4
	addi	s1, s1, 1
	addi	a4, a2, 9
	mv	a2, a3
	bltu	a1, a4, .LBB4_3
.LBB4_4:
	addi	a0, s1, 1
	mv	a1, zero
	call	malloc
	addi	a1, zero, -1
	blt	a1, s2, .LBB4_6
# %bb.5:
	addi	a1, zero, 45
	sb	a1, 0(a0)
.LBB4_6:
	add	a1, a0, s1
	sb	zero, 0(a1)
	beqz	s3, .LBB4_10
# %bb.7:
	srai	a1, s1, 31
	lui	a2, 419430
	addi	a2, a2, 1639
	addi	a6, zero, 10
	addi	a7, zero, 18
.LBB4_8:                                # =>This Inner Loop Header: Depth=1
	mulh	a5, s3, a2
	srli	a3, a5, 31
	srai	a5, a5, 2
	add	a3, a5, a3
	mul	a5, a3, a6
	sub	a5, s3, a5
	addi	a5, a5, 48
	addi	s0, s1, -1
	add	a4, a0, s0
	sb	a5, 0(a4)
	sltu	a4, s0, s1
	add	a1, a1, a4
	addi	a1, a1, -1
	addi	a4, s3, 9
	mv	s1, s0
	mv	s3, a3
	bltu	a7, a4, .LBB4_8
	j	.LBB4_10
.LBB4_9:
	addi	a0, zero, 2
	mv	a1, zero
	call	malloc
	sb	zero, 1(a0)
	addi	a1, zero, 48
	sb	a1, 0(a0)
.LBB4_10:
	lw	s3, 12(sp)
	lw	s2, 16(sp)
	lw	s1, 20(sp)
	lw	s0, 24(sp)
	lw	ra, 28(sp)
	.cfi_restore ra
	.cfi_restore s0
	.cfi_restore s1
	.cfi_restore s2
	.cfi_restore s3
	addi	sp, sp, 32
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end4:
	.size	toString, .Lfunc_end4-toString
	.cfi_endproc
                                        # -- End function
	.globl	getInt                  # -- Begin function getInt
	.p2align	2
	.type	getInt,@function
getInt:                                 # @getInt
	.cfi_startproc
# %bb.0:
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)
	.cfi_offset ra, -4
	lui	a0, %hi(.L.str.2)
	addi	a0, a0, %lo(.L.str.2)
	addi	a1, sp, 8
	call	__isoc99_scanf
	lw	a0, 8(sp)
	lw	ra, 12(sp)
	.cfi_restore ra
	addi	sp, sp, 16
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end5:
	.size	getInt, .Lfunc_end5-getInt
	.cfi_endproc
                                        # -- End function
	.globl	getString               # -- Begin function getString
	.p2align	2
	.type	getString,@function
getString:                              # @getString
	.cfi_startproc
# %bb.0:
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)
	sw	s0, 8(sp)
	.cfi_offset ra, -4
	.cfi_offset s0, -8
	addi	a0, zero, 256
	mv	a1, zero
	call	malloc
	mv	s0, a0
	lui	a0, %hi(.L.str)
	addi	a0, a0, %lo(.L.str)
	mv	a1, s0
	call	__isoc99_scanf
	mv	a0, s0
	lw	s0, 8(sp)
	lw	ra, 12(sp)
	.cfi_restore ra
	.cfi_restore s0
	addi	sp, sp, 16
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end6:
	.size	getString, .Lfunc_end6-getString
	.cfi_endproc
                                        # -- End function
	.globl	_string_length          # -- Begin function _string_length
	.p2align	2
	.type	_string_length,@function
_string_length:                         # @_string_length
	.cfi_startproc
# %bb.0:
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)
	.cfi_offset ra, -4
	call	strlen
	lw	ra, 12(sp)
	.cfi_restore ra
	addi	sp, sp, 16
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end7:
	.size	_string_length, .Lfunc_end7-_string_length
	.cfi_endproc
                                        # -- End function
	.globl	_string_substring       # -- Begin function _string_substring
	.p2align	2
	.type	_string_substring,@function
_string_substring:                      # @_string_substring
	.cfi_startproc
# %bb.0:
	addi	sp, sp, -32
	.cfi_def_cfa_offset 32
	sw	ra, 28(sp)
	sw	s0, 24(sp)
	sw	s1, 20(sp)
	sw	s2, 16(sp)
	sw	s3, 12(sp)
	sw	s4, 8(sp)
	.cfi_offset ra, -4
	.cfi_offset s0, -8
	.cfi_offset s1, -12
	.cfi_offset s2, -16
	.cfi_offset s3, -20
	.cfi_offset s4, -24
	mv	s4, a2
	mv	s0, a1
	mv	s3, a0
	sub	s2, a2, a1
	addi	a0, s2, 1
	srai	a1, a0, 31
	call	malloc
	mv	s1, a0
	bge	s0, s4, .LBB8_2
# %bb.1:
	add	a1, s3, s0
	mv	a0, s1
	mv	a2, s2
	call	memcpy
.LBB8_2:
	add	a0, s1, s2
	sb	zero, 0(a0)
	mv	a0, s1
	lw	s4, 8(sp)
	lw	s3, 12(sp)
	lw	s2, 16(sp)
	lw	s1, 20(sp)
	lw	s0, 24(sp)
	lw	ra, 28(sp)
	.cfi_restore ra
	.cfi_restore s0
	.cfi_restore s1
	.cfi_restore s2
	.cfi_restore s3
	.cfi_restore s4
	addi	sp, sp, 32
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end8:
	.size	_string_substring, .Lfunc_end8-_string_substring
	.cfi_endproc
                                        # -- End function
	.globl	_string_parseInt        # -- Begin function _string_parseInt
	.p2align	2
	.type	_string_parseInt,@function
_string_parseInt:                       # @_string_parseInt
	.cfi_startproc
# %bb.0:
	lbu	a1, 0(a0)
	addi	a6, zero, -1
	addi	a2, zero, 45
	beq	a1, a2, .LBB9_2
# %bb.1:
	addi	a6, zero, 1
.LBB9_2:
	xori	a1, a1, 45
	seqz	a2, a1
	add	a1, a0, a2
	lbu	a7, 0(a1)
	addi	a1, a7, -48
	andi	a1, a1, 255
	addi	a3, zero, 9
	bltu	a3, a1, .LBB9_6
# %bb.3:
	addi	t0, zero, 10
	mv	a5, zero
	mv	a1, zero
.LBB9_4:                                # =>This Inner Loop Header: Depth=1
	addi	a4, a2, 1
	sltu	a2, a4, a2
	add	a5, a5, a2
	mul	a1, a1, t0
	slli	a2, a7, 24
	srai	a2, a2, 24
	add	a1, a1, a2
	add	a2, a0, a4
	addi	a1, a1, -48
	lbu	a7, 0(a2)
	addi	a2, a7, -48
	andi	a3, a2, 255
	mv	a2, a4
	bltu	a3, t0, .LBB9_4
# %bb.5:
	mul	a0, a1, a6
	.cfi_def_cfa_offset 0
	ret
.LBB9_6:
	mul	a0, zero, a6
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end9:
	.size	_string_parseInt, .Lfunc_end9-_string_parseInt
	.cfi_endproc
                                        # -- End function
	.globl	_string_ord             # -- Begin function _string_ord
	.p2align	2
	.type	_string_ord,@function
_string_ord:                            # @_string_ord
	.cfi_startproc
# %bb.0:
	add	a0, a0, a1
	lb	a0, 0(a0)
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end10:
	.size	_string_ord, .Lfunc_end10-_string_ord
	.cfi_endproc
                                        # -- End function
	.globl	_string_concatenate     # -- Begin function _string_concatenate
	.p2align	2
	.type	_string_concatenate,@function
_string_concatenate:                    # @_string_concatenate
	.cfi_startproc
# %bb.0:
	addi	sp, sp, -48
	.cfi_def_cfa_offset 48
	sw	ra, 44(sp)
	sw	s0, 40(sp)
	sw	s1, 36(sp)
	sw	s2, 32(sp)
	sw	s3, 28(sp)
	sw	s4, 24(sp)
	sw	s5, 20(sp)
	sw	s6, 16(sp)
	sw	s7, 12(sp)
	.cfi_offset ra, -4
	.cfi_offset s0, -8
	.cfi_offset s1, -12
	.cfi_offset s2, -16
	.cfi_offset s3, -20
	.cfi_offset s4, -24
	.cfi_offset s5, -28
	.cfi_offset s6, -32
	.cfi_offset s7, -36
	mv	s2, a1
	mv	s3, a0
	call	strlen
	mv	s1, a0
	mv	a0, s2
	call	strlen
	mv	s4, a0
	add	s5, a0, s1
	addi	a0, s5, 1
	srai	a1, a0, 31
	call	malloc
	mv	s0, a0
	addi	s6, zero, -1
	addi	s7, zero, 1
	blt	s1, s7, .LBB11_2
# %bb.1:
	and	a2, s1, s6
	mv	a0, s0
	mv	a1, s3
	call	memcpy
.LBB11_2:
	blt	s4, s7, .LBB11_4
# %bb.3:
	add	a0, s0, s1
	and	a2, s4, s6
	mv	a1, s2
	call	memcpy
.LBB11_4:
	add	a0, s0, s5
	sb	zero, 0(a0)
	mv	a0, s0
	lw	s7, 12(sp)
	lw	s6, 16(sp)
	lw	s5, 20(sp)
	lw	s4, 24(sp)
	lw	s3, 28(sp)
	lw	s2, 32(sp)
	lw	s1, 36(sp)
	lw	s0, 40(sp)
	lw	ra, 44(sp)
	.cfi_restore ra
	.cfi_restore s0
	.cfi_restore s1
	.cfi_restore s2
	.cfi_restore s3
	.cfi_restore s4
	.cfi_restore s5
	.cfi_restore s6
	.cfi_restore s7
	addi	sp, sp, 48
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end11:
	.size	_string_concatenate, .Lfunc_end11-_string_concatenate
	.cfi_endproc
                                        # -- End function
	.globl	_string_eq              # -- Begin function _string_eq
	.p2align	2
	.type	_string_eq,@function
_string_eq:                             # @_string_eq
	.cfi_startproc
# %bb.0:
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)
	.cfi_offset ra, -4
	call	strcmp
	seqz	a0, a0
	lw	ra, 12(sp)
	.cfi_restore ra
	addi	sp, sp, 16
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end12:
	.size	_string_eq, .Lfunc_end12-_string_eq
	.cfi_endproc
                                        # -- End function
	.globl	_string_ne              # -- Begin function _string_ne
	.p2align	2
	.type	_string_ne,@function
_string_ne:                             # @_string_ne
	.cfi_startproc
# %bb.0:
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)
	.cfi_offset ra, -4
	call	strcmp
	snez	a0, a0
	lw	ra, 12(sp)
	.cfi_restore ra
	addi	sp, sp, 16
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end13:
	.size	_string_ne, .Lfunc_end13-_string_ne
	.cfi_endproc
                                        # -- End function
	.globl	_string_slt             # -- Begin function _string_slt
	.p2align	2
	.type	_string_slt,@function
_string_slt:                            # @_string_slt
	.cfi_startproc
# %bb.0:
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)
	.cfi_offset ra, -4
	call	strcmp
	srli	a0, a0, 31
	lw	ra, 12(sp)
	.cfi_restore ra
	addi	sp, sp, 16
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end14:
	.size	_string_slt, .Lfunc_end14-_string_slt
	.cfi_endproc
                                        # -- End function
	.globl	_string_sle             # -- Begin function _string_sle
	.p2align	2
	.type	_string_sle,@function
_string_sle:                            # @_string_sle
	.cfi_startproc
# %bb.0:
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)
	.cfi_offset ra, -4
	call	strcmp
	slti	a0, a0, 1
	lw	ra, 12(sp)
	.cfi_restore ra
	addi	sp, sp, 16
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end15:
	.size	_string_sle, .Lfunc_end15-_string_sle
	.cfi_endproc
                                        # -- End function
	.globl	_string_sgt             # -- Begin function _string_sgt
	.p2align	2
	.type	_string_sgt,@function
_string_sgt:                            # @_string_sgt
	.cfi_startproc
# %bb.0:
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)
	.cfi_offset ra, -4
	call	strcmp
	sgtz	a0, a0
	lw	ra, 12(sp)
	.cfi_restore ra
	addi	sp, sp, 16
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end16:
	.size	_string_sgt, .Lfunc_end16-_string_sgt
	.cfi_endproc
                                        # -- End function
	.globl	_string_sge             # -- Begin function _string_sge
	.p2align	2
	.type	_string_sge,@function
_string_sge:                            # @_string_sge
	.cfi_startproc
# %bb.0:
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)
	.cfi_offset ra, -4
	call	strcmp
	not	a0, a0
	srli	a0, a0, 31
	lw	ra, 12(sp)
	.cfi_restore ra
	addi	sp, sp, 16
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end17:
	.size	_string_sge, .Lfunc_end17-_string_sge
	.cfi_endproc
                                        # -- End function
	.type	.L.str,@object          # @.str
	.section	.rodata.str1.1,"aMS",@progbits,1
.L.str:
	.asciz	"%s"
	.size	.L.str, 3

	.type	.L.str.2,@object        # @.str.2
.L.str.2:
	.asciz	"%d"
	.size	.L.str.2, 3

	.type	.L.str.3,@object        # @.str.3
.L.str.3:
	.asciz	"%d\n"
	.size	.L.str.3, 4


	.ident	"clang version 6.0.0-1ubuntu2 (tags/RELEASE_600/final)"
	.ident	"clang version 6.0.0-1ubuntu2 (tags/RELEASE_600/final)"
	.ident	"clang version 6.0.0-1ubuntu2 (tags/RELEASE_600/final)"
	.section	".note.GNU-stack","",@progbits

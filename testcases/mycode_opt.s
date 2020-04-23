	.text
	.file	"mycode_opt.ll"
	.globl	gcd                     # -- Begin function gcd
	.p2align	2
	.type	gcd,@function
gcd:                                    # @gcd
	.cfi_startproc
# %bb.0:                                # %func_init
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)
	.cfi_offset ra, -4
	mv	a2, a1
	rem	a1, a0, a1
	beqz	a1, .LBB0_2
# %bb.1:                                # %if_notTaken
	mv	a0, a2
	call	gcd
	mv	a2, a0
.LBB0_2:                                # %retBlock
	mv	a0, a2
	lw	ra, 12(sp)
	.cfi_restore ra
	addi	sp, sp, 16
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end0:
	.size	gcd, .Lfunc_end0-gcd
	.cfi_endproc
                                        # -- End function
	.globl	main                    # -- Begin function main
	.p2align	2
	.type	main,@function
main:                                   # @main
	.cfi_startproc
# %bb.0:                                # %retBlock.1
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)
	sw	s0, 8(sp)
	.cfi_offset ra, -4
	.cfi_offset s0, -8
	call	__global_init
	addi	a0, zero, 10
	addi	a1, zero, 1
	call	gcd
	call	toString
	call	println
	lui	a0, 9
	addi	a0, a0, -1878
	lui	s0, 1
	addi	a1, s0, -1009
	call	gcd
	call	toString
	call	println
	addi	a0, s0, -1189
	addi	a1, zero, 1539
	call	gcd
	call	toString
	call	println
	mv	a0, zero
	lw	s0, 8(sp)
	lw	ra, 12(sp)
	.cfi_restore ra
	.cfi_restore s0
	addi	sp, sp, 16
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end1:
	.size	main, .Lfunc_end1-main
	.cfi_endproc
                                        # -- End function
	.globl	__global_init           # -- Begin function __global_init
	.p2align	2
	.type	__global_init,@function
__global_init:                          # @__global_init
	.cfi_startproc
# %bb.0:                                # %init
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end2:
	.size	__global_init, .Lfunc_end2-__global_init
	.cfi_endproc
                                        # -- End function

	.section	".note.GNU-stack","",@progbits

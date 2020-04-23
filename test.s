	.text
	.file	"llvm-link"
	.globl	digt                    # -- Begin function digt
	.p2align	2
	.type	digt,@function
digt:                                   # @digt
	.cfi_startproc
# %bb.0:                                # %func_init
	beqz	a0, .LBB0_3
# %bb.1:                                # %if_after
	addi	a1, zero, 1
	bne	a0, a1, .LBB0_4
# %bb.2:                                # %if_taken.1
	lui	a0, %hi(.conststr.4)
	addi	a1, a0, %lo(.conststr.4)
	mv	a0, a1
	.cfi_def_cfa_offset 0
	ret
.LBB0_3:                                # %if_taken
	lui	a0, %hi(.conststr.3)
	addi	a1, a0, %lo(.conststr.3)
	mv	a0, a1
	.cfi_def_cfa_offset 0
	ret
.LBB0_4:                                # %if_after.1
	addi	a1, zero, 2
	bne	a0, a1, .LBB0_6
# %bb.5:                                # %if_taken.2
	lui	a0, %hi(.conststr.5)
	addi	a1, a0, %lo(.conststr.5)
	mv	a0, a1
	.cfi_def_cfa_offset 0
	ret
.LBB0_6:                                # %if_after.2
	addi	a1, zero, 3
	bne	a0, a1, .LBB0_8
# %bb.7:                                # %if_taken.3
	lui	a0, %hi(.conststr.6)
	addi	a1, a0, %lo(.conststr.6)
	mv	a0, a1
	.cfi_def_cfa_offset 0
	ret
.LBB0_8:                                # %if_after.3
	addi	a1, zero, 4
	bne	a0, a1, .LBB0_10
# %bb.9:                                # %if_taken.4
	lui	a0, %hi(.conststr.7)
	addi	a1, a0, %lo(.conststr.7)
	mv	a0, a1
	.cfi_def_cfa_offset 0
	ret
.LBB0_10:                               # %if_after.4
	addi	a1, zero, 5
	bne	a0, a1, .LBB0_12
# %bb.11:                               # %if_taken.5
	lui	a0, %hi(.conststr.8)
	addi	a1, a0, %lo(.conststr.8)
	mv	a0, a1
	.cfi_def_cfa_offset 0
	ret
.LBB0_12:                               # %if_after.5
	addi	a1, zero, 6
	bne	a0, a1, .LBB0_14
# %bb.13:                               # %if_taken.6
	lui	a0, %hi(.conststr.9)
	addi	a1, a0, %lo(.conststr.9)
	mv	a0, a1
	.cfi_def_cfa_offset 0
	ret
.LBB0_14:                               # %if_after.6
	addi	a1, zero, 7
	bne	a0, a1, .LBB0_16
# %bb.15:                               # %if_taken.7
	lui	a0, %hi(.conststr.10)
	addi	a1, a0, %lo(.conststr.10)
	mv	a0, a1
	.cfi_def_cfa_offset 0
	ret
.LBB0_16:                               # %if_after.7
	addi	a1, zero, 8
	bne	a0, a1, .LBB0_18
# %bb.17:                               # %if_taken.8
	lui	a0, %hi(.conststr.11)
	addi	a1, a0, %lo(.conststr.11)
	mv	a0, a1
	.cfi_def_cfa_offset 0
	ret
.LBB0_18:                               # %if_after.8
	addi	a2, zero, 9
                                        # implicit-def: $x11
	bne	a0, a2, .LBB0_20
# %bb.19:                               # %if_taken.9
	lui	a0, %hi(.conststr.12)
	addi	a1, a0, %lo(.conststr.12)
.LBB0_20:                               # %retBlock
	mv	a0, a1
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end0:
	.size	digt, .Lfunc_end0-digt
	.cfi_endproc
                                        # -- End function
	.globl	s2                      # -- Begin function s2
	.p2align	2
	.type	s2,@function
s2:                                     # @s2
	.cfi_startproc
# %bb.0:                                # %func_init.1
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)
	sw	s0, 8(sp)
	sw	s1, 4(sp)
	sw	s2, 0(sp)
	.cfi_offset ra, -4
	.cfi_offset s0, -8
	.cfi_offset s1, -12
	.cfi_offset s2, -16
	mv	s0, a0
	addi	a0, zero, 9
	blt	a0, s0, .LBB1_2
# %bb.1:                                # %if_taken.10
	mv	a0, s0
	call	digt
	mv	a1, a0
	lui	a0, %hi(.conststr.13)
	addi	a0, a0, %lo(.conststr.13)
	call	_string_concatenate
	lui	a1, %hi(.conststr.14)
	addi	a1, a1, %lo(.conststr.14)
	j	.LBB1_3
.LBB1_2:                                # %if_after.10
	lui	a0, 419430
	addi	a0, a0, 1639
	mulh	a0, s0, a0
	srli	a1, a0, 31
	srai	a0, a0, 2
	add	s1, a0, a1
	mv	a0, s1
	call	digt
	mv	a1, a0
	lui	a0, %hi(.conststr.15)
	addi	a0, a0, %lo(.conststr.15)
	call	_string_concatenate
	mv	s2, a0
	addi	a0, zero, 10
	mul	a0, s1, a0
	sub	a0, s0, a0
	call	digt
	mv	a1, a0
	mv	a0, s2
	call	_string_concatenate
	lui	a1, %hi(.conststr.16)
	addi	a1, a1, %lo(.conststr.16)
.LBB1_3:                                # %retBlock.1
	call	_string_concatenate
	lw	s2, 0(sp)
	lw	s1, 4(sp)
	lw	s0, 8(sp)
	lw	ra, 12(sp)
	.cfi_restore ra
	.cfi_restore s0
	.cfi_restore s1
	.cfi_restore s2
	addi	sp, sp, 16
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end1:
	.size	s2, .Lfunc_end1-s2
	.cfi_endproc
                                        # -- End function
	.globl	c2                      # -- Begin function c2
	.p2align	2
	.type	c2,@function
c2:                                     # @c2
	.cfi_startproc
# %bb.0:                                # %func_init.2
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)
	sw	s0, 8(sp)
	sw	s1, 4(sp)
	sw	s2, 0(sp)
	.cfi_offset ra, -4
	.cfi_offset s0, -8
	.cfi_offset s1, -12
	.cfi_offset s2, -16
	mv	s0, a0
	addi	a0, zero, 9
	blt	a0, s0, .LBB2_2
# %bb.1:                                # %if_taken.11
	mv	a0, s0
	call	digt
	mv	a1, a0
	lui	a0, %hi(.conststr.17)
	addi	a0, a0, %lo(.conststr.17)
	call	_string_concatenate
	lui	a1, %hi(.conststr.18)
	addi	a1, a1, %lo(.conststr.18)
	j	.LBB2_3
.LBB2_2:                                # %if_after.11
	lui	a0, 419430
	addi	a0, a0, 1639
	mulh	a0, s0, a0
	srli	a1, a0, 31
	srai	a0, a0, 2
	add	s1, a0, a1
	mv	a0, s1
	call	digt
	mv	a1, a0
	lui	a0, %hi(.conststr.19)
	addi	a0, a0, %lo(.conststr.19)
	call	_string_concatenate
	mv	s2, a0
	addi	a0, zero, 10
	mul	a0, s1, a0
	sub	a0, s0, a0
	call	digt
	mv	a1, a0
	mv	a0, s2
	call	_string_concatenate
	lui	a1, %hi(.conststr.20)
	addi	a1, a1, %lo(.conststr.20)
.LBB2_3:                                # %retBlock.2
	call	_string_concatenate
	lw	s2, 0(sp)
	lw	s1, 4(sp)
	lw	s0, 8(sp)
	lw	ra, 12(sp)
	.cfi_restore ra
	.cfi_restore s0
	.cfi_restore s1
	.cfi_restore s2
	addi	sp, sp, 16
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end2:
	.size	c2, .Lfunc_end2-c2
	.cfi_endproc
                                        # -- End function
	.globl	main                    # -- Begin function main
	.p2align	2
	.type	main,@function
main:                                   # @main
	.cfi_startproc
# %bb.0:                                # %func_init.3
	addi	sp, sp, -32
	.cfi_def_cfa_offset 32
	sw	ra, 28(sp)
	sw	s0, 24(sp)
	sw	s1, 20(sp)
	sw	s2, 16(sp)
	sw	s3, 12(sp)
	sw	s4, 8(sp)
	sw	s5, 4(sp)
	.cfi_offset ra, -4
	.cfi_offset s0, -8
	.cfi_offset s1, -12
	.cfi_offset s2, -16
	.cfi_offset s3, -20
	.cfi_offset s4, -24
	.cfi_offset s5, -28
	call	__global_init
	lui	a0, %hi(.conststr.21)
	addi	a0, a0, %lo(.conststr.21)
	lui	s4, %hi(c)
	lw	a1, %lo(c)(s4)
	sw	a0, 0(a1)
	lui	a0, %hi(.conststr.22)
	addi	a0, a0, %lo(.conststr.22)
	lw	a1, %lo(c)(s4)
	sw	a0, 4(a1)
	lui	a0, %hi(.conststr.23)
	addi	a0, a0, %lo(.conststr.23)
	lw	a1, %lo(c)(s4)
	sw	a0, 8(a1)
	lui	a0, %hi(.conststr.24)
	addi	a0, a0, %lo(.conststr.24)
	lw	a1, %lo(c)(s4)
	sw	a0, 12(a1)
	lui	a0, %hi(.conststr.25)
	addi	a0, a0, %lo(.conststr.25)
	lw	a1, %lo(c)(s4)
	sw	a0, 16(a1)
	lui	a0, %hi(.conststr.26)
	addi	a0, a0, %lo(.conststr.26)
	lw	a1, %lo(c)(s4)
	sw	a0, 20(a1)
	lui	a0, %hi(.conststr.27)
	addi	a0, a0, %lo(.conststr.27)
	lw	a1, %lo(c)(s4)
	sw	a0, 24(a1)
	lui	a0, %hi(.conststr.28)
	addi	a0, a0, %lo(.conststr.28)
	lw	a1, %lo(c)(s4)
	sw	a0, 28(a1)
	lui	a0, %hi(.conststr.29)
	addi	a0, a0, %lo(.conststr.29)
	lw	a1, %lo(c)(s4)
	sw	a0, 32(a1)
	lui	a0, %hi(.conststr.30)
	addi	a0, a0, %lo(.conststr.30)
	lw	a1, %lo(c)(s4)
	sw	a0, 36(a1)
	lui	a0, %hi(.conststr.31)
	addi	a0, a0, %lo(.conststr.31)
	lw	a1, %lo(c)(s4)
	sw	a0, 40(a1)
	lui	a0, %hi(.conststr.32)
	addi	a0, a0, %lo(.conststr.32)
	lw	a1, %lo(c)(s4)
	sw	a0, 44(a1)
	lui	a0, %hi(.conststr.33)
	addi	a0, a0, %lo(.conststr.33)
	lw	a1, %lo(c)(s4)
	sw	a0, 48(a1)
	lui	a0, %hi(.conststr.34)
	addi	a0, a0, %lo(.conststr.34)
	lw	a1, %lo(c)(s4)
	sw	a0, 52(a1)
	lui	a0, %hi(.conststr.35)
	addi	a0, a0, %lo(.conststr.35)
	lw	a1, %lo(c)(s4)
	sw	a0, 56(a1)
	lui	a0, %hi(.conststr.36)
	addi	a0, a0, %lo(.conststr.36)
	lw	a1, %lo(c)(s4)
	sw	a0, 60(a1)
	lui	a0, %hi(.conststr.37)
	addi	a0, a0, %lo(.conststr.37)
	lw	a1, %lo(c)(s4)
	sw	a0, 64(a1)
	lui	a0, %hi(.conststr.38)
	addi	a0, a0, %lo(.conststr.38)
	lw	a1, %lo(c)(s4)
	sw	a0, 68(a1)
	lui	a0, %hi(.conststr.39)
	addi	a0, a0, %lo(.conststr.39)
	lw	a1, %lo(c)(s4)
	sw	a0, 72(a1)
	lui	a0, %hi(.conststr.40)
	addi	a0, a0, %lo(.conststr.40)
	lw	a1, %lo(c)(s4)
	sw	a0, 76(a1)
	lui	a0, %hi(.conststr.41)
	addi	a0, a0, %lo(.conststr.41)
	lw	a1, %lo(c)(s4)
	sw	a0, 80(a1)
	lui	a0, %hi(.conststr.42)
	addi	a0, a0, %lo(.conststr.42)
	lw	a1, %lo(c)(s4)
	sw	a0, 84(a1)
	lui	a0, %hi(.conststr.43)
	addi	a0, a0, %lo(.conststr.43)
	lw	a1, %lo(c)(s4)
	sw	a0, 88(a1)
	lui	a0, %hi(.conststr.44)
	addi	a0, a0, %lo(.conststr.44)
	lw	a1, %lo(c)(s4)
	sw	a0, 92(a1)
	lui	a0, %hi(.conststr.45)
	addi	a0, a0, %lo(.conststr.45)
	lw	a1, %lo(c)(s4)
	sw	a0, 96(a1)
	lui	a0, %hi(.conststr.46)
	addi	a0, a0, %lo(.conststr.46)
	lw	a1, %lo(c)(s4)
	sw	a0, 100(a1)
	lui	a0, %hi(.conststr.47)
	addi	a0, a0, %lo(.conststr.47)
	lw	a1, %lo(c)(s4)
	sw	a0, 104(a1)
	lui	a0, %hi(.conststr.48)
	addi	a0, a0, %lo(.conststr.48)
	lw	a1, %lo(c)(s4)
	sw	a0, 108(a1)
	lui	a0, %hi(.conststr.49)
	addi	a0, a0, %lo(.conststr.49)
	lw	a1, %lo(c)(s4)
	sw	a0, 112(a1)
	lui	a0, %hi(.conststr.50)
	addi	a0, a0, %lo(.conststr.50)
	lw	a1, %lo(c)(s4)
	sw	a0, 116(a1)
	lui	a0, %hi(.conststr.51)
	addi	a0, a0, %lo(.conststr.51)
	lw	a1, %lo(c)(s4)
	sw	a0, 120(a1)
	lui	a0, %hi(.conststr.52)
	addi	a0, a0, %lo(.conststr.52)
	lw	a1, %lo(c)(s4)
	sw	a0, 124(a1)
	lui	a0, %hi(.conststr.53)
	addi	a0, a0, %lo(.conststr.53)
	lw	a1, %lo(c)(s4)
	sw	a0, 128(a1)
	lui	a0, %hi(.conststr.54)
	addi	a0, a0, %lo(.conststr.54)
	lw	a1, %lo(c)(s4)
	sw	a0, 132(a1)
	lui	a0, %hi(.conststr.55)
	addi	a0, a0, %lo(.conststr.55)
	lw	a1, %lo(c)(s4)
	sw	a0, 136(a1)
	lui	a0, %hi(.conststr.56)
	addi	a0, a0, %lo(.conststr.56)
	lw	a1, %lo(c)(s4)
	sw	a0, 140(a1)
	lui	a0, %hi(.conststr.57)
	addi	a0, a0, %lo(.conststr.57)
	lw	a1, %lo(c)(s4)
	sw	a0, 144(a1)
	lui	a0, %hi(.conststr.58)
	addi	a0, a0, %lo(.conststr.58)
	lw	a1, %lo(c)(s4)
	sw	a0, 148(a1)
	lui	a0, %hi(.conststr.59)
	addi	a0, a0, %lo(.conststr.59)
	lw	a1, %lo(c)(s4)
	sw	a0, 152(a1)
	lui	a0, %hi(.conststr.60)
	addi	a0, a0, %lo(.conststr.60)
	lw	a1, %lo(c)(s4)
	sw	a0, 156(a1)
	lui	a0, %hi(.conststr.61)
	addi	a0, a0, %lo(.conststr.61)
	lw	a1, %lo(c)(s4)
	sw	a0, 160(a1)
	lui	a0, %hi(.conststr.62)
	addi	a0, a0, %lo(.conststr.62)
	lw	a1, %lo(c)(s4)
	sw	a0, 164(a1)
	lui	a0, %hi(.conststr.63)
	addi	a0, a0, %lo(.conststr.63)
	lw	a1, %lo(c)(s4)
	sw	a0, 168(a1)
	lui	a0, %hi(.conststr.64)
	addi	a0, a0, %lo(.conststr.64)
	lw	a1, %lo(c)(s4)
	sw	a0, 172(a1)
	lui	a0, %hi(.conststr.65)
	addi	a0, a0, %lo(.conststr.65)
	lw	a1, %lo(c)(s4)
	sw	a0, 176(a1)
	lui	a0, %hi(.conststr.66)
	addi	a0, a0, %lo(.conststr.66)
	lw	a1, %lo(c)(s4)
	sw	a0, 180(a1)
	lui	a0, %hi(.conststr.67)
	addi	a0, a0, %lo(.conststr.67)
	lw	a1, %lo(c)(s4)
	sw	a0, 184(a1)
	lui	a0, %hi(.conststr.68)
	addi	a0, a0, %lo(.conststr.68)
	lw	a1, %lo(c)(s4)
	sw	a0, 188(a1)
	lui	a0, %hi(.conststr.69)
	addi	a0, a0, %lo(.conststr.69)
	lw	a1, %lo(c)(s4)
	sw	a0, 192(a1)
	lui	a0, %hi(.conststr.70)
	addi	a0, a0, %lo(.conststr.70)
	lw	a1, %lo(c)(s4)
	sw	a0, 196(a1)
	lui	a0, %hi(.conststr.71)
	addi	a0, a0, %lo(.conststr.71)
	lw	a1, %lo(c)(s4)
	sw	a0, 200(a1)
	lui	a0, %hi(.conststr.72)
	addi	a0, a0, %lo(.conststr.72)
	lw	a1, %lo(c)(s4)
	sw	a0, 204(a1)
	lui	a0, %hi(.conststr.73)
	addi	a0, a0, %lo(.conststr.73)
	lw	a1, %lo(c)(s4)
	sw	a0, 208(a1)
	lui	a0, %hi(.conststr.74)
	addi	a0, a0, %lo(.conststr.74)
	lw	a1, %lo(c)(s4)
	sw	a0, 212(a1)
	lui	a0, %hi(.conststr.75)
	addi	a0, a0, %lo(.conststr.75)
	lw	a1, %lo(c)(s4)
	sw	a0, 216(a1)
	lui	a0, %hi(.conststr.76)
	addi	a0, a0, %lo(.conststr.76)
	lw	a1, %lo(c)(s4)
	sw	a0, 220(a1)
	lui	a0, %hi(.conststr.77)
	addi	a0, a0, %lo(.conststr.77)
	lw	a1, %lo(c)(s4)
	sw	a0, 224(a1)
	lui	a0, %hi(.conststr.78)
	addi	a0, a0, %lo(.conststr.78)
	lw	a1, %lo(c)(s4)
	sw	a0, 228(a1)
	lui	a0, %hi(.conststr.79)
	addi	a0, a0, %lo(.conststr.79)
	lw	a1, %lo(c)(s4)
	sw	a0, 232(a1)
	lui	a0, %hi(.conststr.80)
	addi	a0, a0, %lo(.conststr.80)
	lw	a1, %lo(c)(s4)
	sw	a0, 236(a1)
	lui	a0, %hi(.conststr.81)
	addi	a0, a0, %lo(.conststr.81)
	lw	a1, %lo(c)(s4)
	sw	a0, 240(a1)
	lui	a0, %hi(.conststr.82)
	addi	a0, a0, %lo(.conststr.82)
	lw	a1, %lo(c)(s4)
	sw	a0, 244(a1)
	lui	a0, %hi(.conststr.83)
	addi	a0, a0, %lo(.conststr.83)
	lw	a1, %lo(c)(s4)
	sw	a0, 248(a1)
	lui	a0, %hi(.conststr.84)
	addi	a0, a0, %lo(.conststr.84)
	lw	a1, %lo(c)(s4)
	sw	a0, 252(a1)
	lui	a0, %hi(.conststr.85)
	addi	a0, a0, %lo(.conststr.85)
	lw	a1, %lo(c)(s4)
	sw	a0, 256(a1)
	lui	a0, %hi(.conststr.86)
	addi	a0, a0, %lo(.conststr.86)
	lw	a1, %lo(c)(s4)
	sw	a0, 260(a1)
	lui	a0, %hi(.conststr.87)
	addi	a0, a0, %lo(.conststr.87)
	lw	a1, %lo(c)(s4)
	sw	a0, 264(a1)
	lui	a0, %hi(.conststr.88)
	addi	a0, a0, %lo(.conststr.88)
	lw	a1, %lo(c)(s4)
	sw	a0, 268(a1)
	lui	a0, %hi(.conststr.89)
	addi	a0, a0, %lo(.conststr.89)
	lw	a1, %lo(c)(s4)
	sw	a0, 272(a1)
	lui	a0, %hi(.conststr.90)
	addi	a0, a0, %lo(.conststr.90)
	lw	a1, %lo(c)(s4)
	sw	a0, 276(a1)
	lui	a0, %hi(.conststr.91)
	addi	a0, a0, %lo(.conststr.91)
	lw	a1, %lo(c)(s4)
	sw	a0, 280(a1)
	lui	a0, %hi(.conststr.92)
	addi	a0, a0, %lo(.conststr.92)
	lw	a1, %lo(c)(s4)
	sw	a0, 284(a1)
	lui	a0, %hi(.conststr.93)
	addi	a0, a0, %lo(.conststr.93)
	lw	a1, %lo(c)(s4)
	sw	a0, 288(a1)
	lui	a0, %hi(.conststr.94)
	addi	a0, a0, %lo(.conststr.94)
	lw	a1, %lo(c)(s4)
	sw	a0, 292(a1)
	lui	a0, %hi(.conststr.95)
	addi	a0, a0, %lo(.conststr.95)
	lw	a1, %lo(c)(s4)
	sw	a0, 296(a1)
	lui	a0, %hi(.conststr.96)
	addi	a0, a0, %lo(.conststr.96)
	lw	a1, %lo(c)(s4)
	sw	a0, 300(a1)
	lui	a0, %hi(.conststr.97)
	addi	a0, a0, %lo(.conststr.97)
	lw	a1, %lo(c)(s4)
	sw	a0, 304(a1)
	lui	a0, %hi(.conststr.98)
	addi	a0, a0, %lo(.conststr.98)
	lw	a1, %lo(c)(s4)
	sw	a0, 308(a1)
	lui	a0, %hi(.conststr.99)
	addi	a0, a0, %lo(.conststr.99)
	lw	a1, %lo(c)(s4)
	sw	a0, 312(a1)
	lui	a0, %hi(.conststr.100)
	addi	a0, a0, %lo(.conststr.100)
	lw	a1, %lo(c)(s4)
	sw	a0, 316(a1)
	lui	a0, %hi(.conststr.101)
	addi	a0, a0, %lo(.conststr.101)
	lw	a1, %lo(c)(s4)
	sw	a0, 320(a1)
	lui	a0, %hi(.conststr.102)
	addi	a0, a0, %lo(.conststr.102)
	lw	a1, %lo(c)(s4)
	sw	a0, 324(a1)
	lui	a0, %hi(.conststr.103)
	addi	a0, a0, %lo(.conststr.103)
	lw	a1, %lo(c)(s4)
	sw	a0, 328(a1)
	lui	a0, %hi(.conststr.104)
	addi	a0, a0, %lo(.conststr.104)
	lw	a1, %lo(c)(s4)
	sw	a0, 332(a1)
	lui	a0, %hi(.conststr.105)
	addi	a0, a0, %lo(.conststr.105)
	lw	a1, %lo(c)(s4)
	sw	a0, 336(a1)
	lui	a0, %hi(.conststr.106)
	addi	a0, a0, %lo(.conststr.106)
	lw	a1, %lo(c)(s4)
	sw	a0, 340(a1)
	lui	a0, %hi(.conststr.107)
	addi	a0, a0, %lo(.conststr.107)
	lw	a1, %lo(c)(s4)
	sw	a0, 344(a1)
	lui	a0, %hi(.conststr.108)
	addi	a0, a0, %lo(.conststr.108)
	lw	a1, %lo(c)(s4)
	sw	a0, 348(a1)
	lui	a0, %hi(.conststr.109)
	addi	a0, a0, %lo(.conststr.109)
	lw	a1, %lo(c)(s4)
	sw	a0, 352(a1)
	lui	a0, %hi(.conststr.110)
	addi	a0, a0, %lo(.conststr.110)
	lw	a1, %lo(c)(s4)
	sw	a0, 356(a1)
	lui	a0, %hi(.conststr.111)
	addi	a0, a0, %lo(.conststr.111)
	lw	a1, %lo(c)(s4)
	sw	a0, 360(a1)
	lui	a0, %hi(.conststr.112)
	addi	a0, a0, %lo(.conststr.112)
	lw	a1, %lo(c)(s4)
	sw	a0, 364(a1)
	lui	a0, %hi(.conststr.113)
	addi	a0, a0, %lo(.conststr.113)
	lw	a1, %lo(c)(s4)
	sw	a0, 368(a1)
	lui	a0, %hi(.conststr.114)
	addi	a0, a0, %lo(.conststr.114)
	lui	s3, %hi(s)
	lw	a1, %lo(s)(s3)
	sw	a0, 0(a1)
	lui	a0, %hi(.conststr.115)
	addi	a0, a0, %lo(.conststr.115)
	lw	a1, %lo(s)(s3)
	sw	a0, 4(a1)
	lui	a0, %hi(.conststr.116)
	addi	a0, a0, %lo(.conststr.116)
	lw	a1, %lo(s)(s3)
	sw	a0, 8(a1)
	lui	a0, %hi(.conststr.117)
	addi	a0, a0, %lo(.conststr.117)
	lw	a1, %lo(s)(s3)
	sw	a0, 12(a1)
	lui	a0, %hi(.conststr.118)
	addi	a0, a0, %lo(.conststr.118)
	lw	a1, %lo(s)(s3)
	sw	a0, 16(a1)
	lui	a0, %hi(.conststr.119)
	addi	a0, a0, %lo(.conststr.119)
	lw	a1, %lo(s)(s3)
	sw	a0, 20(a1)
	lui	a0, %hi(.conststr.120)
	addi	a0, a0, %lo(.conststr.120)
	lw	a1, %lo(s)(s3)
	sw	a0, 24(a1)
	lui	a0, %hi(.conststr.121)
	addi	a0, a0, %lo(.conststr.121)
	lw	a1, %lo(s)(s3)
	sw	a0, 28(a1)
	lui	a0, %hi(.conststr.122)
	addi	a0, a0, %lo(.conststr.122)
	lw	a1, %lo(s)(s3)
	sw	a0, 32(a1)
	lui	a0, %hi(.conststr.123)
	addi	a0, a0, %lo(.conststr.123)
	lw	a1, %lo(s)(s3)
	sw	a0, 36(a1)
	lui	a0, %hi(.conststr.124)
	addi	a0, a0, %lo(.conststr.124)
	lw	a1, %lo(s)(s3)
	sw	a0, 40(a1)
	lui	a0, %hi(.conststr.125)
	addi	a0, a0, %lo(.conststr.125)
	lw	a1, %lo(s)(s3)
	sw	a0, 44(a1)
	lui	a0, %hi(.conststr.126)
	addi	a0, a0, %lo(.conststr.126)
	lw	a1, %lo(s)(s3)
	sw	a0, 48(a1)
	lui	a0, %hi(.conststr.127)
	addi	a0, a0, %lo(.conststr.127)
	lw	a1, %lo(s)(s3)
	sw	a0, 52(a1)
	lui	a0, %hi(.conststr.128)
	addi	a0, a0, %lo(.conststr.128)
	lw	a1, %lo(s)(s3)
	sw	a0, 56(a1)
	lui	a0, %hi(.conststr.129)
	addi	a0, a0, %lo(.conststr.129)
	lw	a1, %lo(s)(s3)
	sw	a0, 60(a1)
	lui	a0, %hi(.conststr.130)
	addi	a0, a0, %lo(.conststr.130)
	lw	a1, %lo(s)(s3)
	sw	a0, 64(a1)
	lui	a0, %hi(.conststr.131)
	addi	a0, a0, %lo(.conststr.131)
	lw	a1, %lo(s)(s3)
	sw	a0, 68(a1)
	lui	a0, %hi(.conststr.132)
	addi	a0, a0, %lo(.conststr.132)
	lw	a1, %lo(s)(s3)
	sw	a0, 72(a1)
	lui	a0, %hi(.conststr.133)
	addi	a0, a0, %lo(.conststr.133)
	lw	a1, %lo(s)(s3)
	sw	a0, 76(a1)
	lui	a0, %hi(.conststr.134)
	addi	a0, a0, %lo(.conststr.134)
	lw	a1, %lo(s)(s3)
	sw	a0, 80(a1)
	lui	a0, %hi(.conststr.135)
	addi	a0, a0, %lo(.conststr.135)
	lw	a1, %lo(s)(s3)
	sw	a0, 84(a1)
	lui	a0, %hi(.conststr.136)
	addi	a0, a0, %lo(.conststr.136)
	lw	a1, %lo(s)(s3)
	sw	a0, 88(a1)
	lui	a0, %hi(.conststr.137)
	addi	a0, a0, %lo(.conststr.137)
	lw	a1, %lo(s)(s3)
	sw	a0, 92(a1)
	lui	a0, %hi(.conststr.138)
	addi	a0, a0, %lo(.conststr.138)
	lw	a1, %lo(s)(s3)
	sw	a0, 96(a1)
	lui	a0, %hi(.conststr.139)
	addi	a0, a0, %lo(.conststr.139)
	lw	a1, %lo(s)(s3)
	sw	a0, 100(a1)
	lui	a0, %hi(.conststr.140)
	addi	a0, a0, %lo(.conststr.140)
	lw	a1, %lo(s)(s3)
	sw	a0, 104(a1)
	lui	a0, %hi(.conststr.141)
	addi	a0, a0, %lo(.conststr.141)
	lw	a1, %lo(s)(s3)
	sw	a0, 108(a1)
	lui	a0, %hi(.conststr.142)
	addi	a0, a0, %lo(.conststr.142)
	lw	a1, %lo(s)(s3)
	sw	a0, 112(a1)
	lui	a0, %hi(.conststr.143)
	addi	a0, a0, %lo(.conststr.143)
	lw	a1, %lo(s)(s3)
	sw	a0, 116(a1)
	lui	a0, %hi(.conststr.144)
	addi	a0, a0, %lo(.conststr.144)
	lw	a1, %lo(s)(s3)
	sw	a0, 120(a1)
	lui	a0, %hi(.conststr.145)
	addi	a0, a0, %lo(.conststr.145)
	lw	a1, %lo(s)(s3)
	sw	a0, 124(a1)
	lw	a0, %lo(c)(s4)
	lw	a1, 328(a0)
	lw	a0, 324(a0)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 284(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 304(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 276(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 0(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 264(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 284(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 276(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 328(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 28(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 284(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 304(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 328(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 0(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 344(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 32(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 356(a1)
	call	_string_concatenate
	call	println
	lw	a0, %lo(c)(s4)
	lw	a1, 272(a0)
	lw	a0, 284(a0)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 28(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 344(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 60(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 32(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 268(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 328(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 332(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 304(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 0(a1)
	call	_string_concatenate
	lui	s2, %hi(a2q)
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 60(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 104(a1)
	call	_string_concatenate
	call	println
	lw	a0, %lo(c)(s4)
	lw	a1, 272(a0)
	lw	a0, 284(a0)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 28(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 344(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 64(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 32(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 268(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 328(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 332(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 304(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 0(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 64(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 104(a1)
	call	_string_concatenate
	call	println
	lw	a0, %lo(c)(s4)
	lw	a1, 272(a0)
	lw	a0, 284(a0)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 28(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 344(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 68(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 32(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 268(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 328(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 332(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 304(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 0(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 68(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 104(a1)
	call	_string_concatenate
	call	println
	lw	a0, %lo(c)(s4)
	lw	a1, 272(a0)
	lw	a0, 284(a0)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 28(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 344(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 72(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 32(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 268(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 328(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 332(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 304(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 0(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 72(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 104(a1)
	call	_string_concatenate
	call	println
	lw	a0, %lo(c)(s4)
	lw	a1, 272(a0)
	lw	a0, 284(a0)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 28(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 344(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 76(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 32(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 268(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 328(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 332(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 304(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 0(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 76(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 104(a1)
	call	_string_concatenate
	call	println
	lw	a0, %lo(c)(s4)
	lw	a1, 272(a0)
	lw	a0, 284(a0)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 28(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 344(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 80(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 32(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 268(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 328(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 332(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 304(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 0(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 80(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 104(a1)
	call	_string_concatenate
	call	println
	lw	a0, %lo(c)(s4)
	lw	a1, 272(a0)
	lw	a0, 284(a0)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 28(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 344(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 84(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 32(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 268(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 328(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 332(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 304(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 0(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 84(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 104(a1)
	call	_string_concatenate
	call	println
	lw	a0, %lo(c)(s4)
	lw	a1, 272(a0)
	lw	a0, 284(a0)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 28(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 344(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 88(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 32(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 268(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 328(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 332(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 304(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 0(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 88(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 104(a1)
	call	_string_concatenate
	call	println
	lw	a0, %lo(c)(s4)
	lw	a1, 272(a0)
	lw	a0, 284(a0)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 28(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 344(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 92(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 32(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 268(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 328(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 332(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 304(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 0(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 92(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 104(a1)
	call	_string_concatenate
	call	println
	lw	a0, %lo(c)(s4)
	lw	a1, 272(a0)
	lw	a0, 284(a0)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 28(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 344(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 96(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 32(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 268(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 328(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 332(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 304(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 0(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 96(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 104(a1)
	call	_string_concatenate
	call	println
	lw	a0, %lo(c)(s4)
	lw	a0, 364(a0)
	call	println
	lw	a0, %lo(c)(s4)
	lw	a1, 328(a0)
	lw	a0, 324(a0)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 284(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 304(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 276(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 232(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 236(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 0(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 324(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 304(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 268(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 340(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 0(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 324(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 328(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 284(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 304(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 276(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 232(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 68(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 80(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 84(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 236(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 104(a1)
	call	_string_concatenate
	call	println
	lw	a0, %lo(c)(s4)
	lw	a1, 328(a0)
	lw	a0, 324(a0)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 284(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 304(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 276(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 232(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 236(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 0(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 260(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 304(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 268(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 340(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 0(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 324(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 328(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 284(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 304(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 276(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 232(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 68(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 80(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 84(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 236(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 104(a1)
	call	_string_concatenate
	call	println
	lw	a0, %lo(c)(s4)
	lw	a1, 328(a0)
	lw	a0, 324(a0)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 284(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 304(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 276(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 0(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 324(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 68(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 28(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 284(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 304(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 328(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 0(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 324(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 324(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 32(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 356(a1)
	call	_string_concatenate
	call	println
	lw	a0, %lo(c)(s4)
	lw	a1, 272(a0)
	lw	a0, 284(a0)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 28(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 324(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 324(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 108(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 96(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 32(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 268(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 328(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 332(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 304(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 0(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 324(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 232(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 40(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 264(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 284(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 276(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 328(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 28(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 324(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 324(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 32(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 40(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 236(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 104(a1)
	call	_string_concatenate
	call	println
	lw	a0, %lo(c)(s4)
	lw	a1, 268(a0)
	lw	a0, 320(a0)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 328(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 332(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 304(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 0(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 324(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 232(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 40(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 264(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 284(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 276(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 328(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 28(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 324(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 324(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 56(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 64(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 60(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 32(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 40(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 264(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 284(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 276(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 328(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 28(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 324(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 324(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 16(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 64(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 60(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 32(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 40(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 236(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 104(a1)
	call	_string_concatenate
	call	println
	lw	a0, %lo(c)(s4)
	lw	a0, 364(a0)
	call	println
	lw	a0, %lo(c)(s4)
	lw	a1, 328(a0)
	lw	a0, 324(a0)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 284(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 304(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 276(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 0(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 260(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 68(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 28(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 284(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 304(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 328(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 0(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 260(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 260(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 32(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 356(a1)
	call	_string_concatenate
	call	println
	lw	a0, %lo(c)(s4)
	lw	a1, 272(a0)
	lw	a0, 284(a0)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 28(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 260(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 260(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 108(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 96(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 32(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 268(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 328(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 332(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 304(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 0(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 260(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 232(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 40(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 264(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 284(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 276(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 328(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 28(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 260(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 260(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 32(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 40(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 236(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 104(a1)
	call	_string_concatenate
	call	println
	lw	a0, %lo(c)(s4)
	lw	a1, 268(a0)
	lw	a0, 320(a0)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 328(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 332(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 304(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 0(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 260(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 232(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 40(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 264(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 284(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 276(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 328(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 28(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 260(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 260(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 56(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 64(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 60(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 32(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 40(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 264(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 284(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 276(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 328(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 28(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 260(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 260(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 16(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 64(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 60(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 32(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 40(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 236(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 104(a1)
	call	_string_concatenate
	call	println
	lw	a0, %lo(c)(s4)
	lw	a0, 364(a0)
	call	println
	lw	a0, %lo(c)(s4)
	lw	a1, 328(a0)
	lw	a0, 324(a0)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 284(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 304(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 276(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 0(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 260(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 308(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 104(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 104(a1)
	call	_string_concatenate
	call	println
	lw	a0, %lo(c)(s4)
	lw	a1, 328(a0)
	lw	a0, 324(a0)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 284(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 304(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 276(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 0(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 252(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 68(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 316(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lui	s0, %hi(a2b)
	lw	a1, %lo(a2b)(s0)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 104(a1)
	call	_string_concatenate
	call	println
	lw	a0, %lo(c)(s4)
	lw	a1, 328(a0)
	lw	a0, 324(a0)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 320(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 284(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 304(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 276(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 0(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 252(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 68(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 256(a1)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 112(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(a2b)(s0)
	call	_string_concatenate
	lw	a1, %lo(a2b)(s0)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	lw	a1, 104(a1)
	call	_string_concatenate
	call	println
	lw	a0, %lo(s)(s3)
	lw	a0, 0(a0)
	call	println
	addi	s5, zero, 92
	lui	s3, %hi(co)
	mv	s1, zero
	mv	s0, zero
	blt	s5, s0, .LBB3_2
.LBB3_1:                                # %for_incr
                                        # =>This Inner Loop Header: Depth=1
	mv	a0, s0
	call	c2
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(c)(s4)
	add	a1, a1, s1
	lw	a1, 0(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s2)
	call	_string_concatenate
	lw	a1, %lo(co)(s3)
	call	_string_concatenate
	call	println
	addi	s1, s1, 4
	addi	s0, s0, 1
	bge	s5, s0, .LBB3_1
.LBB3_2:                                # %for_after
	addi	s2, zero, 31
	lui	s5, %hi(a2q)
	lui	s3, %hi(s)
	lui	s4, %hi(co)
	mv	s1, zero
	mv	s0, zero
	blt	s2, s0, .LBB3_4
.LBB3_3:                                # %for_incr.1
                                        # =>This Inner Loop Header: Depth=1
	mv	a0, s0
	call	s2
	lw	a1, %lo(a2q)(s5)
	call	_string_concatenate
	lw	a1, %lo(s)(s3)
	add	a1, a1, s1
	lw	a1, 0(a1)
	call	_string_concatenate
	lw	a1, %lo(a2q)(s5)
	call	_string_concatenate
	lw	a1, %lo(co)(s4)
	call	_string_concatenate
	call	println
	addi	s1, s1, 4
	addi	s0, s0, 1
	bge	s2, s0, .LBB3_3
.LBB3_4:                                # %for_after.1
	addi	s0, zero, 1
	addi	s1, zero, 4
	addi	s2, zero, 31
	lui	s3, %hi(s)
	blt	s2, s0, .LBB3_6
.LBB3_5:                                # %for_body.2
                                        # =>This Inner Loop Header: Depth=1
	lw	a0, %lo(s)(s3)
	add	a0, a0, s1
	lw	a0, 0(a0)
	call	println
	addi	s1, s1, 4
	addi	s0, s0, 1
	bge	s2, s0, .LBB3_5
.LBB3_6:                                # %for_after.2
	mv	a0, zero
	lw	s5, 4(sp)
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
	.cfi_restore s5
	addi	sp, sp, 32
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end3:
	.size	main, .Lfunc_end3-main
	.cfi_endproc
                                        # -- End function
	.globl	__global_init           # -- Begin function __global_init
	.p2align	2
	.type	__global_init,@function
__global_init:                          # @__global_init
	.cfi_startproc
# %bb.0:                                # %init
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)
	sw	s0, 8(sp)
	sw	s1, 4(sp)
	.cfi_offset ra, -4
	.cfi_offset s0, -8
	.cfi_offset s1, -12
	lui	a0, 1
	addi	s0, a0, -2044
	mv	a0, s0
	mv	a1, zero
	call	malloc
	addi	s1, zero, 256
	sw	s1, 0(a0)
	addi	a0, a0, 4
	lui	a1, %hi(s)
	sw	a0, %lo(s)(a1)
	mv	a0, s0
	mv	a1, zero
	call	malloc
	sw	s1, 0(a0)
	addi	a0, a0, 4
	lui	a1, %hi(c)
	sw	a0, %lo(c)(a1)
	lui	a0, %hi(.conststr.2)
	addi	a0, a0, %lo(.conststr.2)
	lui	a1, %hi(a2b)
	sw	a0, %lo(a2b)(a1)
	lui	a0, %hi(.conststr.1)
	addi	a0, a0, %lo(.conststr.1)
	lui	a1, %hi(a2q)
	sw	a0, %lo(a2q)(a1)
	lui	a0, %hi(.conststr)
	addi	a0, a0, %lo(.conststr)
	lui	a1, %hi(co)
	sw	a0, %lo(co)(a1)
	lw	s1, 4(sp)
	lw	s0, 8(sp)
	lw	ra, 12(sp)
	.cfi_restore ra
	.cfi_restore s0
	.cfi_restore s1
	addi	sp, sp, 16
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end4:
	.size	__global_init, .Lfunc_end4-__global_init
	.cfi_endproc
                                        # -- End function
	.globl	print                   # -- Begin function print
	.p2align	2
	.type	print,@function
print:                                  # @print
	.cfi_startproc
# %bb.0:
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)
	sw	s0, 8(sp)
	.cfi_offset ra, -4
	.cfi_offset s0, -8
	addi	s0, sp, 16
	.cfi_def_cfa s0, 0
	sw	a0, -16(s0)
	lw	a1, -16(s0)
	lui	a0, %hi(.L.str)
	addi	a0, a0, %lo(.L.str)
	call	printf
	lw	s0, 8(sp)
	.cfi_def_cfa sp, 16
	lw	ra, 12(sp)
	.cfi_restore ra
	.cfi_restore s0
	addi	sp, sp, 16
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end5:
	.size	print, .Lfunc_end5-print
	.cfi_endproc
                                        # -- End function
	.globl	println                 # -- Begin function println
	.p2align	2
	.type	println,@function
println:                                # @println
	.cfi_startproc
# %bb.0:
	addi	sp, sp, -16
	.cfi_def_cfa_offset 16
	sw	ra, 12(sp)
	sw	s0, 8(sp)
	.cfi_offset ra, -4
	.cfi_offset s0, -8
	addi	s0, sp, 16
	.cfi_def_cfa s0, 0
	sw	a0, -16(s0)
	lw	a1, -16(s0)
	lui	a0, %hi(.L.str.1)
	addi	a0, a0, %lo(.L.str.1)
	call	printf
	lw	s0, 8(sp)
	.cfi_def_cfa sp, 16
	lw	ra, 12(sp)
	.cfi_restore ra
	.cfi_restore s0
	addi	sp, sp, 16
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end6:
	.size	println, .Lfunc_end6-println
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
	sw	s0, 8(sp)
	.cfi_offset ra, -4
	.cfi_offset s0, -8
	addi	s0, sp, 16
	.cfi_def_cfa s0, 0
	sw	a0, -12(s0)
	lw	a1, -12(s0)
	lui	a0, %hi(.L.str.2)
	addi	a0, a0, %lo(.L.str.2)
	call	printf
	lw	s0, 8(sp)
	.cfi_def_cfa sp, 16
	lw	ra, 12(sp)
	.cfi_restore ra
	.cfi_restore s0
	addi	sp, sp, 16
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end7:
	.size	printInt, .Lfunc_end7-printInt
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
	sw	s0, 8(sp)
	.cfi_offset ra, -4
	.cfi_offset s0, -8
	addi	s0, sp, 16
	.cfi_def_cfa s0, 0
	sw	a0, -12(s0)
	lw	a1, -12(s0)
	lui	a0, %hi(.L.str.3)
	addi	a0, a0, %lo(.L.str.3)
	call	printf
	lw	s0, 8(sp)
	.cfi_def_cfa sp, 16
	lw	ra, 12(sp)
	.cfi_restore ra
	.cfi_restore s0
	addi	sp, sp, 16
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end8:
	.size	printlnInt, .Lfunc_end8-printlnInt
	.cfi_endproc
                                        # -- End function
	.globl	toString                # -- Begin function toString
	.p2align	2
	.type	toString,@function
toString:                               # @toString
	.cfi_startproc
# %bb.0:
	addi	sp, sp, -48
	.cfi_def_cfa_offset 48
	sw	ra, 44(sp)
	sw	s0, 40(sp)
	.cfi_offset ra, -4
	.cfi_offset s0, -8
	addi	s0, sp, 48
	.cfi_def_cfa s0, 0
	sw	a0, -20(s0)
	lw	a0, -20(s0)
	bnez	a0, .LBB9_2
	j	.LBB9_1
.LBB9_1:
	addi	a0, zero, 2
	mv	a1, zero
	call	malloc
	sw	a0, -24(s0)
	lw	a0, -24(s0)
	addi	a1, zero, 48
	sb	a1, 0(a0)
	lw	a0, -24(s0)
	sb	zero, 1(a0)
	lw	a0, -24(s0)
	sw	a0, -16(s0)
	j	.LBB9_17
.LBB9_2:
	lw	a0, -20(s0)
	sw	a0, -28(s0)
	lw	a0, -20(s0)
	srli	a0, a0, 31
	sw	a0, -32(s0)
	lw	a0, -20(s0)
	addi	a1, zero, -1
	blt	a1, a0, .LBB9_4
	j	.LBB9_3
.LBB9_3:
	lw	a0, -20(s0)
	neg	a0, a0
	j	.LBB9_5
.LBB9_4:
	lw	a0, -20(s0)
	j	.LBB9_5
.LBB9_5:
	sw	a0, -20(s0)
	j	.LBB9_6
.LBB9_6:                                # =>This Inner Loop Header: Depth=1
	lw	a0, -20(s0)
	beqz	a0, .LBB9_8
	j	.LBB9_7
.LBB9_7:                                #   in Loop: Header=BB9_6 Depth=1
	lw	a0, -20(s0)
	lui	a1, 419430
	addi	a1, a1, 1639
	mulh	a0, a0, a1
	srli	a1, a0, 31
	srai	a0, a0, 2
	add	a0, a0, a1
	sw	a0, -20(s0)
	lw	a0, -32(s0)
	addi	a0, a0, 1
	sw	a0, -32(s0)
	j	.LBB9_6
.LBB9_8:
	lw	a0, -32(s0)
	addi	a0, a0, 1
	srai	a1, a0, 31
	call	malloc
	sw	a0, -40(s0)
	lw	a0, -28(s0)
	addi	a1, zero, -1
	blt	a1, a0, .LBB9_10
	j	.LBB9_9
.LBB9_9:
	lw	a0, -40(s0)
	addi	a1, zero, 45
	sb	a1, 0(a0)
	j	.LBB9_10
.LBB9_10:
	lw	a0, -28(s0)
	addi	a1, zero, -1
	blt	a1, a0, .LBB9_12
	j	.LBB9_11
.LBB9_11:
	lw	a0, -28(s0)
	neg	a0, a0
	j	.LBB9_13
.LBB9_12:
	lw	a0, -28(s0)
	j	.LBB9_13
.LBB9_13:
	sw	a0, -28(s0)
	lw	a0, -40(s0)
	lw	a1, -32(s0)
	addi	a2, a1, -1
	sw	a2, -32(s0)
	add	a0, a0, a1
	sb	zero, 0(a0)
	j	.LBB9_14
.LBB9_14:                               # =>This Inner Loop Header: Depth=1
	lw	a0, -28(s0)
	beqz	a0, .LBB9_16
	j	.LBB9_15
.LBB9_15:                               #   in Loop: Header=BB9_14 Depth=1
	lw	a0, -28(s0)
	lui	a1, 419430
	addi	a1, a1, 1639
	mulh	a2, a0, a1
	srli	a3, a2, 31
	srli	a2, a2, 2
	add	a2, a2, a3
	addi	a3, zero, 10
	mul	a2, a2, a3
	sub	a0, a0, a2
	addi	a0, a0, 48
	lw	a2, -40(s0)
	lw	a3, -32(s0)
	addi	a4, a3, -1
	sw	a4, -32(s0)
	add	a2, a2, a3
	sb	a0, 0(a2)
	lw	a0, -28(s0)
	mulh	a0, a0, a1
	srli	a1, a0, 31
	srai	a0, a0, 2
	add	a0, a0, a1
	sw	a0, -28(s0)
	j	.LBB9_14
.LBB9_16:
	lw	a0, -40(s0)
	sw	a0, -16(s0)
	j	.LBB9_17
.LBB9_17:
	lw	a0, -16(s0)
	lw	s0, 40(sp)
	.cfi_def_cfa sp, 48
	lw	ra, 44(sp)
	.cfi_restore ra
	.cfi_restore s0
	addi	sp, sp, 48
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end9:
	.size	toString, .Lfunc_end9-toString
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
	sw	s0, 8(sp)
	.cfi_offset ra, -4
	.cfi_offset s0, -8
	addi	s0, sp, 16
	.cfi_def_cfa s0, 0
	lui	a0, %hi(.L.str.2)
	addi	a0, a0, %lo(.L.str.2)
	addi	a1, s0, -12
	call	__isoc99_scanf
	lw	a0, -12(s0)
	lw	s0, 8(sp)
	.cfi_def_cfa sp, 16
	lw	ra, 12(sp)
	.cfi_restore ra
	.cfi_restore s0
	addi	sp, sp, 16
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end10:
	.size	getInt, .Lfunc_end10-getInt
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
	addi	s0, sp, 16
	.cfi_def_cfa s0, 0
	addi	a0, zero, 256
	mv	a1, zero
	call	malloc
	sw	a0, -16(s0)
	lw	a1, -16(s0)
	lui	a0, %hi(.L.str)
	addi	a0, a0, %lo(.L.str)
	call	__isoc99_scanf
	lw	a0, -16(s0)
	lw	s0, 8(sp)
	.cfi_def_cfa sp, 16
	lw	ra, 12(sp)
	.cfi_restore ra
	.cfi_restore s0
	addi	sp, sp, 16
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end11:
	.size	getString, .Lfunc_end11-getString
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
	sw	s0, 8(sp)
	.cfi_offset ra, -4
	.cfi_offset s0, -8
	addi	s0, sp, 16
	.cfi_def_cfa s0, 0
	sw	a0, -16(s0)
	lw	a0, -16(s0)
	call	strlen
	lw	s0, 8(sp)
	.cfi_def_cfa sp, 16
	lw	ra, 12(sp)
	.cfi_restore ra
	.cfi_restore s0
	addi	sp, sp, 16
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end12:
	.size	_string_length, .Lfunc_end12-_string_length
	.cfi_endproc
                                        # -- End function
	.globl	_string_substring       # -- Begin function _string_substring
	.p2align	2
	.type	_string_substring,@function
_string_substring:                      # @_string_substring
	.cfi_startproc
# %bb.0:
	addi	sp, sp, -48
	.cfi_def_cfa_offset 48
	sw	ra, 44(sp)
	sw	s0, 40(sp)
	.cfi_offset ra, -4
	.cfi_offset s0, -8
	addi	s0, sp, 48
	.cfi_def_cfa s0, 0
	sw	a0, -16(s0)
	sw	a1, -20(s0)
	sw	a2, -24(s0)
	lw	a0, -24(s0)
	lw	a1, -20(s0)
	sub	a0, a0, a1
	addi	a0, a0, 1
	srai	a1, a0, 31
	call	malloc
	sw	a0, -32(s0)
	lw	a0, -20(s0)
	sw	a0, -36(s0)
	j	.LBB13_1
.LBB13_1:                               # =>This Inner Loop Header: Depth=1
	lw	a0, -36(s0)
	lw	a1, -24(s0)
	bge	a0, a1, .LBB13_4
	j	.LBB13_2
.LBB13_2:                               #   in Loop: Header=BB13_1 Depth=1
	lw	a0, -16(s0)
	lw	a1, -36(s0)
	add	a0, a0, a1
	lb	a0, 0(a0)
	lw	a2, -32(s0)
	lw	a3, -20(s0)
	sub	a1, a1, a3
	add	a1, a2, a1
	sb	a0, 0(a1)
	j	.LBB13_3
.LBB13_3:                               #   in Loop: Header=BB13_1 Depth=1
	lw	a0, -36(s0)
	addi	a0, a0, 1
	sw	a0, -36(s0)
	j	.LBB13_1
.LBB13_4:
	lw	a0, -32(s0)
	lw	a1, -24(s0)
	lw	a2, -20(s0)
	sub	a1, a1, a2
	add	a0, a0, a1
	sb	zero, 0(a0)
	lw	a0, -32(s0)
	lw	s0, 40(sp)
	.cfi_def_cfa sp, 48
	lw	ra, 44(sp)
	.cfi_restore ra
	.cfi_restore s0
	addi	sp, sp, 48
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end13:
	.size	_string_substring, .Lfunc_end13-_string_substring
	.cfi_endproc
                                        # -- End function
	.globl	_string_parseInt        # -- Begin function _string_parseInt
	.p2align	2
	.type	_string_parseInt,@function
_string_parseInt:                       # @_string_parseInt
	.cfi_startproc
# %bb.0:
	addi	sp, sp, -32
	.cfi_def_cfa_offset 32
	sw	ra, 28(sp)
	sw	s0, 24(sp)
	.cfi_offset ra, -4
	.cfi_offset s0, -8
	addi	s0, sp, 32
	.cfi_def_cfa s0, 0
	sw	a0, -16(s0)
	sw	zero, -20(s0)
	addi	a0, zero, 1
	sw	a0, -24(s0)
	sw	zero, -28(s0)
	lw	a0, -16(s0)
	lb	a0, 0(a0)
	addi	a1, zero, 45
	bne	a0, a1, .LBB14_2
	j	.LBB14_1
.LBB14_1:
	addi	a0, zero, -1
	sw	a0, -24(s0)
	addi	a0, zero, 1
	sw	a0, -28(s0)
	j	.LBB14_2
.LBB14_2:
	j	.LBB14_3
.LBB14_3:                               # =>This Inner Loop Header: Depth=1
	lw	a0, -16(s0)
	lw	a1, -28(s0)
	add	a0, a0, a1
	lb	a1, 0(a0)
	addi	a2, zero, 48
	mv	a0, zero
	blt	a1, a2, .LBB14_5
	j	.LBB14_4
.LBB14_4:                               #   in Loop: Header=BB14_3 Depth=1
	lw	a0, -16(s0)
	lw	a1, -28(s0)
	add	a0, a0, a1
	lb	a0, 0(a0)
	slti	a0, a0, 58
	j	.LBB14_5
.LBB14_5:                               #   in Loop: Header=BB14_3 Depth=1
	andi	a0, a0, 1
	beqz	a0, .LBB14_7
	j	.LBB14_6
.LBB14_6:                               #   in Loop: Header=BB14_3 Depth=1
	lw	a0, -20(s0)
	addi	a1, zero, 10
	mul	a0, a0, a1
	lw	a1, -16(s0)
	lw	a2, -28(s0)
	addi	a3, a2, 1
	sw	a3, -28(s0)
	add	a1, a1, a2
	lb	a1, 0(a1)
	add	a0, a0, a1
	addi	a0, a0, -48
	sw	a0, -20(s0)
	j	.LBB14_3
.LBB14_7:
	lw	a0, -20(s0)
	lw	a1, -24(s0)
	mul	a0, a0, a1
	lw	s0, 24(sp)
	.cfi_def_cfa sp, 32
	lw	ra, 28(sp)
	.cfi_restore ra
	.cfi_restore s0
	addi	sp, sp, 32
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end14:
	.size	_string_parseInt, .Lfunc_end14-_string_parseInt
	.cfi_endproc
                                        # -- End function
	.globl	_string_ord             # -- Begin function _string_ord
	.p2align	2
	.type	_string_ord,@function
_string_ord:                            # @_string_ord
	.cfi_startproc
# %bb.0:
	addi	sp, sp, -32
	.cfi_def_cfa_offset 32
	sw	ra, 28(sp)
	sw	s0, 24(sp)
	.cfi_offset ra, -4
	.cfi_offset s0, -8
	addi	s0, sp, 32
	.cfi_def_cfa s0, 0
	sw	a0, -16(s0)
	sw	a1, -20(s0)
	lw	a0, -16(s0)
	lw	a1, -20(s0)
	add	a0, a0, a1
	lb	a0, 0(a0)
	lw	s0, 24(sp)
	.cfi_def_cfa sp, 32
	lw	ra, 28(sp)
	.cfi_restore ra
	.cfi_restore s0
	addi	sp, sp, 32
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end15:
	.size	_string_ord, .Lfunc_end15-_string_ord
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
	.cfi_offset ra, -4
	.cfi_offset s0, -8
	addi	s0, sp, 48
	.cfi_def_cfa s0, 0
	sw	a0, -16(s0)
	sw	a1, -24(s0)
	lw	a0, -16(s0)
	call	strlen
	sw	a0, -28(s0)
	lw	a0, -24(s0)
	call	strlen
	sw	a0, -32(s0)
	lw	a0, -28(s0)
	lw	a1, -32(s0)
	add	a0, a0, a1
	addi	a0, a0, 1
	srai	a1, a0, 31
	call	malloc
	sw	a0, -40(s0)
	sw	zero, -44(s0)
	j	.LBB16_1
.LBB16_1:                               # =>This Inner Loop Header: Depth=1
	lw	a0, -44(s0)
	lw	a1, -28(s0)
	bge	a0, a1, .LBB16_4
	j	.LBB16_2
.LBB16_2:                               #   in Loop: Header=BB16_1 Depth=1
	lw	a0, -16(s0)
	lw	a1, -44(s0)
	add	a0, a0, a1
	lb	a0, 0(a0)
	lw	a2, -40(s0)
	add	a1, a2, a1
	sb	a0, 0(a1)
	j	.LBB16_3
.LBB16_3:                               #   in Loop: Header=BB16_1 Depth=1
	lw	a0, -44(s0)
	addi	a0, a0, 1
	sw	a0, -44(s0)
	j	.LBB16_1
.LBB16_4:
	sw	zero, -48(s0)
	j	.LBB16_5
.LBB16_5:                               # =>This Inner Loop Header: Depth=1
	lw	a0, -48(s0)
	lw	a1, -32(s0)
	bge	a0, a1, .LBB16_8
	j	.LBB16_6
.LBB16_6:                               #   in Loop: Header=BB16_5 Depth=1
	lw	a0, -24(s0)
	lw	a1, -48(s0)
	add	a0, a0, a1
	lb	a0, 0(a0)
	lw	a2, -40(s0)
	lw	a3, -28(s0)
	add	a1, a1, a3
	add	a1, a2, a1
	sb	a0, 0(a1)
	j	.LBB16_7
.LBB16_7:                               #   in Loop: Header=BB16_5 Depth=1
	lw	a0, -48(s0)
	addi	a0, a0, 1
	sw	a0, -48(s0)
	j	.LBB16_5
.LBB16_8:
	lw	a0, -40(s0)
	lw	a1, -28(s0)
	lw	a2, -32(s0)
	add	a1, a1, a2
	add	a0, a0, a1
	sb	zero, 0(a0)
	lw	a0, -40(s0)
	lw	s0, 40(sp)
	.cfi_def_cfa sp, 48
	lw	ra, 44(sp)
	.cfi_restore ra
	.cfi_restore s0
	addi	sp, sp, 48
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end16:
	.size	_string_concatenate, .Lfunc_end16-_string_concatenate
	.cfi_endproc
                                        # -- End function
	.globl	_string_eq              # -- Begin function _string_eq
	.p2align	2
	.type	_string_eq,@function
_string_eq:                             # @_string_eq
	.cfi_startproc
# %bb.0:
	addi	sp, sp, -32
	.cfi_def_cfa_offset 32
	sw	ra, 28(sp)
	sw	s0, 24(sp)
	.cfi_offset ra, -4
	.cfi_offset s0, -8
	addi	s0, sp, 32
	.cfi_def_cfa s0, 0
	sw	a0, -16(s0)
	sw	a1, -24(s0)
	lw	a0, -16(s0)
	lw	a1, -24(s0)
	call	strcmp
	seqz	a0, a0
	lw	s0, 24(sp)
	.cfi_def_cfa sp, 32
	lw	ra, 28(sp)
	.cfi_restore ra
	.cfi_restore s0
	addi	sp, sp, 32
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end17:
	.size	_string_eq, .Lfunc_end17-_string_eq
	.cfi_endproc
                                        # -- End function
	.globl	_string_ne              # -- Begin function _string_ne
	.p2align	2
	.type	_string_ne,@function
_string_ne:                             # @_string_ne
	.cfi_startproc
# %bb.0:
	addi	sp, sp, -32
	.cfi_def_cfa_offset 32
	sw	ra, 28(sp)
	sw	s0, 24(sp)
	.cfi_offset ra, -4
	.cfi_offset s0, -8
	addi	s0, sp, 32
	.cfi_def_cfa s0, 0
	sw	a0, -16(s0)
	sw	a1, -24(s0)
	lw	a0, -16(s0)
	lw	a1, -24(s0)
	call	strcmp
	snez	a0, a0
	lw	s0, 24(sp)
	.cfi_def_cfa sp, 32
	lw	ra, 28(sp)
	.cfi_restore ra
	.cfi_restore s0
	addi	sp, sp, 32
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end18:
	.size	_string_ne, .Lfunc_end18-_string_ne
	.cfi_endproc
                                        # -- End function
	.globl	_string_slt             # -- Begin function _string_slt
	.p2align	2
	.type	_string_slt,@function
_string_slt:                            # @_string_slt
	.cfi_startproc
# %bb.0:
	addi	sp, sp, -32
	.cfi_def_cfa_offset 32
	sw	ra, 28(sp)
	sw	s0, 24(sp)
	.cfi_offset ra, -4
	.cfi_offset s0, -8
	addi	s0, sp, 32
	.cfi_def_cfa s0, 0
	sw	a0, -16(s0)
	sw	a1, -24(s0)
	lw	a0, -16(s0)
	lw	a1, -24(s0)
	call	strcmp
	srli	a0, a0, 31
	lw	s0, 24(sp)
	.cfi_def_cfa sp, 32
	lw	ra, 28(sp)
	.cfi_restore ra
	.cfi_restore s0
	addi	sp, sp, 32
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end19:
	.size	_string_slt, .Lfunc_end19-_string_slt
	.cfi_endproc
                                        # -- End function
	.globl	_string_sle             # -- Begin function _string_sle
	.p2align	2
	.type	_string_sle,@function
_string_sle:                            # @_string_sle
	.cfi_startproc
# %bb.0:
	addi	sp, sp, -32
	.cfi_def_cfa_offset 32
	sw	ra, 28(sp)
	sw	s0, 24(sp)
	.cfi_offset ra, -4
	.cfi_offset s0, -8
	addi	s0, sp, 32
	.cfi_def_cfa s0, 0
	sw	a0, -16(s0)
	sw	a1, -24(s0)
	lw	a0, -16(s0)
	lw	a1, -24(s0)
	call	strcmp
	slti	a0, a0, 1
	lw	s0, 24(sp)
	.cfi_def_cfa sp, 32
	lw	ra, 28(sp)
	.cfi_restore ra
	.cfi_restore s0
	addi	sp, sp, 32
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end20:
	.size	_string_sle, .Lfunc_end20-_string_sle
	.cfi_endproc
                                        # -- End function
	.globl	_string_sgt             # -- Begin function _string_sgt
	.p2align	2
	.type	_string_sgt,@function
_string_sgt:                            # @_string_sgt
	.cfi_startproc
# %bb.0:
	addi	sp, sp, -32
	.cfi_def_cfa_offset 32
	sw	ra, 28(sp)
	sw	s0, 24(sp)
	.cfi_offset ra, -4
	.cfi_offset s0, -8
	addi	s0, sp, 32
	.cfi_def_cfa s0, 0
	sw	a0, -16(s0)
	sw	a1, -24(s0)
	lw	a0, -16(s0)
	lw	a1, -24(s0)
	call	strcmp
	sgtz	a0, a0
	lw	s0, 24(sp)
	.cfi_def_cfa sp, 32
	lw	ra, 28(sp)
	.cfi_restore ra
	.cfi_restore s0
	addi	sp, sp, 32
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end21:
	.size	_string_sgt, .Lfunc_end21-_string_sgt
	.cfi_endproc
                                        # -- End function
	.globl	_string_sge             # -- Begin function _string_sge
	.p2align	2
	.type	_string_sge,@function
_string_sge:                            # @_string_sge
	.cfi_startproc
# %bb.0:
	addi	sp, sp, -32
	.cfi_def_cfa_offset 32
	sw	ra, 28(sp)
	sw	s0, 24(sp)
	.cfi_offset ra, -4
	.cfi_offset s0, -8
	addi	s0, sp, 32
	.cfi_def_cfa s0, 0
	sw	a0, -16(s0)
	sw	a1, -24(s0)
	lw	a0, -16(s0)
	lw	a1, -24(s0)
	call	strcmp
	not	a0, a0
	srli	a0, a0, 31
	lw	s0, 24(sp)
	.cfi_def_cfa sp, 32
	lw	ra, 28(sp)
	.cfi_restore ra
	.cfi_restore s0
	addi	sp, sp, 32
	.cfi_def_cfa_offset 0
	ret
.Lfunc_end22:
	.size	_string_sge, .Lfunc_end22-_string_sge
	.cfi_endproc
                                        # -- End function
	.type	s,@object               # @s
	.section	.sbss,"aw",@nobits
	.globl	s
	.p2align	3
s:
	.word	0
	.size	s, 4

	.type	c,@object               # @c
	.globl	c
	.p2align	3
c:
	.word	0
	.size	c, 4

	.type	.conststr,@object       # @.conststr
	.section	.rodata,"a",@progbits
	.globl	.conststr
.conststr:
	.asciz	";"
	.size	.conststr, 2

	.type	co,@object              # @co
	.section	.sbss,"aw",@nobits
	.globl	co
	.p2align	3
co:
	.word	0
	.size	co, 4

	.type	.conststr.1,@object     # @.conststr.1
	.section	.rodata,"a",@progbits
	.globl	.conststr.1
.conststr.1:
	.asciz	"\""
	.size	.conststr.1, 2

	.type	a2q,@object             # @a2q
	.section	.sbss,"aw",@nobits
	.globl	a2q
	.p2align	3
a2q:
	.word	0
	.size	a2q, 4

	.type	.conststr.2,@object     # @.conststr.2
	.section	.rodata,"a",@progbits
	.globl	.conststr.2
.conststr.2:
	.asciz	"\\"
	.size	.conststr.2, 2

	.type	a2b,@object             # @a2b
	.section	.sbss,"aw",@nobits
	.globl	a2b
	.p2align	3
a2b:
	.word	0
	.size	a2b, 4

	.type	.conststr.3,@object     # @.conststr.3
	.section	.rodata,"a",@progbits
	.globl	.conststr.3
.conststr.3:
	.asciz	"0"
	.size	.conststr.3, 2

	.type	.conststr.4,@object     # @.conststr.4
	.globl	.conststr.4
.conststr.4:
	.asciz	"1"
	.size	.conststr.4, 2

	.type	.conststr.5,@object     # @.conststr.5
	.globl	.conststr.5
.conststr.5:
	.asciz	"2"
	.size	.conststr.5, 2

	.type	.conststr.6,@object     # @.conststr.6
	.globl	.conststr.6
.conststr.6:
	.asciz	"3"
	.size	.conststr.6, 2

	.type	.conststr.7,@object     # @.conststr.7
	.globl	.conststr.7
.conststr.7:
	.asciz	"4"
	.size	.conststr.7, 2

	.type	.conststr.8,@object     # @.conststr.8
	.globl	.conststr.8
.conststr.8:
	.asciz	"5"
	.size	.conststr.8, 2

	.type	.conststr.9,@object     # @.conststr.9
	.globl	.conststr.9
.conststr.9:
	.asciz	"6"
	.size	.conststr.9, 2

	.type	.conststr.10,@object    # @.conststr.10
	.globl	.conststr.10
.conststr.10:
	.asciz	"7"
	.size	.conststr.10, 2

	.type	.conststr.11,@object    # @.conststr.11
	.globl	.conststr.11
.conststr.11:
	.asciz	"8"
	.size	.conststr.11, 2

	.type	.conststr.12,@object    # @.conststr.12
	.globl	.conststr.12
.conststr.12:
	.asciz	"9"
	.size	.conststr.12, 2

	.type	.conststr.13,@object    # @.conststr.13
	.globl	.conststr.13
.conststr.13:
	.asciz	"s["
	.size	.conststr.13, 3

	.type	.conststr.14,@object    # @.conststr.14
	.globl	.conststr.14
.conststr.14:
	.asciz	"]="
	.size	.conststr.14, 3

	.type	.conststr.15,@object    # @.conststr.15
	.globl	.conststr.15
.conststr.15:
	.asciz	"s["
	.size	.conststr.15, 3

	.type	.conststr.16,@object    # @.conststr.16
	.globl	.conststr.16
.conststr.16:
	.asciz	"]="
	.size	.conststr.16, 3

	.type	.conststr.17,@object    # @.conststr.17
	.globl	.conststr.17
.conststr.17:
	.asciz	"c["
	.size	.conststr.17, 3

	.type	.conststr.18,@object    # @.conststr.18
	.globl	.conststr.18
.conststr.18:
	.asciz	"]="
	.size	.conststr.18, 3

	.type	.conststr.19,@object    # @.conststr.19
	.globl	.conststr.19
.conststr.19:
	.asciz	"c["
	.size	.conststr.19, 3

	.type	.conststr.20,@object    # @.conststr.20
	.globl	.conststr.20
.conststr.20:
	.asciz	"]="
	.size	.conststr.20, 3

	.type	.conststr.21,@object    # @.conststr.21
	.globl	.conststr.21
.conststr.21:
	.asciz	" "
	.size	.conststr.21, 2

	.type	.conststr.22,@object    # @.conststr.22
	.globl	.conststr.22
.conststr.22:
	.asciz	"!"
	.size	.conststr.22, 2

	.type	.conststr.23,@object    # @.conststr.23
	.globl	.conststr.23
.conststr.23:
	.asciz	"#"
	.size	.conststr.23, 2

	.type	.conststr.24,@object    # @.conststr.24
	.globl	.conststr.24
.conststr.24:
	.asciz	"$"
	.size	.conststr.24, 2

	.type	.conststr.25,@object    # @.conststr.25
	.globl	.conststr.25
.conststr.25:
	.asciz	"%"
	.size	.conststr.25, 2

	.type	.conststr.26,@object    # @.conststr.26
	.globl	.conststr.26
.conststr.26:
	.asciz	"&"
	.size	.conststr.26, 2

	.type	.conststr.27,@object    # @.conststr.27
	.globl	.conststr.27
.conststr.27:
	.asciz	"'"
	.size	.conststr.27, 2

	.type	.conststr.28,@object    # @.conststr.28
	.globl	.conststr.28
.conststr.28:
	.asciz	"("
	.size	.conststr.28, 2

	.type	.conststr.29,@object    # @.conststr.29
	.globl	.conststr.29
.conststr.29:
	.asciz	")"
	.size	.conststr.29, 2

	.type	.conststr.30,@object    # @.conststr.30
	.globl	.conststr.30
.conststr.30:
	.asciz	"*"
	.size	.conststr.30, 2

	.type	.conststr.31,@object    # @.conststr.31
	.globl	.conststr.31
.conststr.31:
	.asciz	"+"
	.size	.conststr.31, 2

	.type	.conststr.32,@object    # @.conststr.32
	.globl	.conststr.32
.conststr.32:
	.asciz	","
	.size	.conststr.32, 2

	.type	.conststr.33,@object    # @.conststr.33
	.globl	.conststr.33
.conststr.33:
	.asciz	"-"
	.size	.conststr.33, 2

	.type	.conststr.34,@object    # @.conststr.34
	.globl	.conststr.34
.conststr.34:
	.asciz	"."
	.size	.conststr.34, 2

	.type	.conststr.35,@object    # @.conststr.35
	.globl	.conststr.35
.conststr.35:
	.asciz	"/"
	.size	.conststr.35, 2

	.type	.conststr.36,@object    # @.conststr.36
	.globl	.conststr.36
.conststr.36:
	.asciz	"0"
	.size	.conststr.36, 2

	.type	.conststr.37,@object    # @.conststr.37
	.globl	.conststr.37
.conststr.37:
	.asciz	"1"
	.size	.conststr.37, 2

	.type	.conststr.38,@object    # @.conststr.38
	.globl	.conststr.38
.conststr.38:
	.asciz	"2"
	.size	.conststr.38, 2

	.type	.conststr.39,@object    # @.conststr.39
	.globl	.conststr.39
.conststr.39:
	.asciz	"3"
	.size	.conststr.39, 2

	.type	.conststr.40,@object    # @.conststr.40
	.globl	.conststr.40
.conststr.40:
	.asciz	"4"
	.size	.conststr.40, 2

	.type	.conststr.41,@object    # @.conststr.41
	.globl	.conststr.41
.conststr.41:
	.asciz	"5"
	.size	.conststr.41, 2

	.type	.conststr.42,@object    # @.conststr.42
	.globl	.conststr.42
.conststr.42:
	.asciz	"6"
	.size	.conststr.42, 2

	.type	.conststr.43,@object    # @.conststr.43
	.globl	.conststr.43
.conststr.43:
	.asciz	"7"
	.size	.conststr.43, 2

	.type	.conststr.44,@object    # @.conststr.44
	.globl	.conststr.44
.conststr.44:
	.asciz	"8"
	.size	.conststr.44, 2

	.type	.conststr.45,@object    # @.conststr.45
	.globl	.conststr.45
.conststr.45:
	.asciz	"9"
	.size	.conststr.45, 2

	.type	.conststr.46,@object    # @.conststr.46
	.globl	.conststr.46
.conststr.46:
	.asciz	":"
	.size	.conststr.46, 2

	.type	.conststr.47,@object    # @.conststr.47
	.globl	.conststr.47
.conststr.47:
	.asciz	";"
	.size	.conststr.47, 2

	.type	.conststr.48,@object    # @.conststr.48
	.globl	.conststr.48
.conststr.48:
	.asciz	"<"
	.size	.conststr.48, 2

	.type	.conststr.49,@object    # @.conststr.49
	.globl	.conststr.49
.conststr.49:
	.asciz	"="
	.size	.conststr.49, 2

	.type	.conststr.50,@object    # @.conststr.50
	.globl	.conststr.50
.conststr.50:
	.asciz	">"
	.size	.conststr.50, 2

	.type	.conststr.51,@object    # @.conststr.51
	.globl	.conststr.51
.conststr.51:
	.asciz	"?"
	.size	.conststr.51, 2

	.type	.conststr.52,@object    # @.conststr.52
	.globl	.conststr.52
.conststr.52:
	.asciz	"@"
	.size	.conststr.52, 2

	.type	.conststr.53,@object    # @.conststr.53
	.globl	.conststr.53
.conststr.53:
	.asciz	"A"
	.size	.conststr.53, 2

	.type	.conststr.54,@object    # @.conststr.54
	.globl	.conststr.54
.conststr.54:
	.asciz	"B"
	.size	.conststr.54, 2

	.type	.conststr.55,@object    # @.conststr.55
	.globl	.conststr.55
.conststr.55:
	.asciz	"C"
	.size	.conststr.55, 2

	.type	.conststr.56,@object    # @.conststr.56
	.globl	.conststr.56
.conststr.56:
	.asciz	"D"
	.size	.conststr.56, 2

	.type	.conststr.57,@object    # @.conststr.57
	.globl	.conststr.57
.conststr.57:
	.asciz	"E"
	.size	.conststr.57, 2

	.type	.conststr.58,@object    # @.conststr.58
	.globl	.conststr.58
.conststr.58:
	.asciz	"F"
	.size	.conststr.58, 2

	.type	.conststr.59,@object    # @.conststr.59
	.globl	.conststr.59
.conststr.59:
	.asciz	"G"
	.size	.conststr.59, 2

	.type	.conststr.60,@object    # @.conststr.60
	.globl	.conststr.60
.conststr.60:
	.asciz	"H"
	.size	.conststr.60, 2

	.type	.conststr.61,@object    # @.conststr.61
	.globl	.conststr.61
.conststr.61:
	.asciz	"I"
	.size	.conststr.61, 2

	.type	.conststr.62,@object    # @.conststr.62
	.globl	.conststr.62
.conststr.62:
	.asciz	"J"
	.size	.conststr.62, 2

	.type	.conststr.63,@object    # @.conststr.63
	.globl	.conststr.63
.conststr.63:
	.asciz	"K"
	.size	.conststr.63, 2

	.type	.conststr.64,@object    # @.conststr.64
	.globl	.conststr.64
.conststr.64:
	.asciz	"L"
	.size	.conststr.64, 2

	.type	.conststr.65,@object    # @.conststr.65
	.globl	.conststr.65
.conststr.65:
	.asciz	"M"
	.size	.conststr.65, 2

	.type	.conststr.66,@object    # @.conststr.66
	.globl	.conststr.66
.conststr.66:
	.asciz	"N"
	.size	.conststr.66, 2

	.type	.conststr.67,@object    # @.conststr.67
	.globl	.conststr.67
.conststr.67:
	.asciz	"O"
	.size	.conststr.67, 2

	.type	.conststr.68,@object    # @.conststr.68
	.globl	.conststr.68
.conststr.68:
	.asciz	"P"
	.size	.conststr.68, 2

	.type	.conststr.69,@object    # @.conststr.69
	.globl	.conststr.69
.conststr.69:
	.asciz	"Q"
	.size	.conststr.69, 2

	.type	.conststr.70,@object    # @.conststr.70
	.globl	.conststr.70
.conststr.70:
	.asciz	"R"
	.size	.conststr.70, 2

	.type	.conststr.71,@object    # @.conststr.71
	.globl	.conststr.71
.conststr.71:
	.asciz	"S"
	.size	.conststr.71, 2

	.type	.conststr.72,@object    # @.conststr.72
	.globl	.conststr.72
.conststr.72:
	.asciz	"T"
	.size	.conststr.72, 2

	.type	.conststr.73,@object    # @.conststr.73
	.globl	.conststr.73
.conststr.73:
	.asciz	"U"
	.size	.conststr.73, 2

	.type	.conststr.74,@object    # @.conststr.74
	.globl	.conststr.74
.conststr.74:
	.asciz	"V"
	.size	.conststr.74, 2

	.type	.conststr.75,@object    # @.conststr.75
	.globl	.conststr.75
.conststr.75:
	.asciz	"W"
	.size	.conststr.75, 2

	.type	.conststr.76,@object    # @.conststr.76
	.globl	.conststr.76
.conststr.76:
	.asciz	"X"
	.size	.conststr.76, 2

	.type	.conststr.77,@object    # @.conststr.77
	.globl	.conststr.77
.conststr.77:
	.asciz	"Y"
	.size	.conststr.77, 2

	.type	.conststr.78,@object    # @.conststr.78
	.globl	.conststr.78
.conststr.78:
	.asciz	"Z"
	.size	.conststr.78, 2

	.type	.conststr.79,@object    # @.conststr.79
	.globl	.conststr.79
.conststr.79:
	.asciz	"["
	.size	.conststr.79, 2

	.type	.conststr.80,@object    # @.conststr.80
	.globl	.conststr.80
.conststr.80:
	.asciz	"]"
	.size	.conststr.80, 2

	.type	.conststr.81,@object    # @.conststr.81
	.globl	.conststr.81
.conststr.81:
	.asciz	"^"
	.size	.conststr.81, 2

	.type	.conststr.82,@object    # @.conststr.82
	.globl	.conststr.82
.conststr.82:
	.asciz	"_"
	.size	.conststr.82, 2

	.type	.conststr.83,@object    # @.conststr.83
	.globl	.conststr.83
.conststr.83:
	.asciz	"`"
	.size	.conststr.83, 2

	.type	.conststr.84,@object    # @.conststr.84
	.globl	.conststr.84
.conststr.84:
	.asciz	"a"
	.size	.conststr.84, 2

	.type	.conststr.85,@object    # @.conststr.85
	.globl	.conststr.85
.conststr.85:
	.asciz	"b"
	.size	.conststr.85, 2

	.type	.conststr.86,@object    # @.conststr.86
	.globl	.conststr.86
.conststr.86:
	.asciz	"c"
	.size	.conststr.86, 2

	.type	.conststr.87,@object    # @.conststr.87
	.globl	.conststr.87
.conststr.87:
	.asciz	"d"
	.size	.conststr.87, 2

	.type	.conststr.88,@object    # @.conststr.88
	.globl	.conststr.88
.conststr.88:
	.asciz	"e"
	.size	.conststr.88, 2

	.type	.conststr.89,@object    # @.conststr.89
	.globl	.conststr.89
.conststr.89:
	.asciz	"f"
	.size	.conststr.89, 2

	.type	.conststr.90,@object    # @.conststr.90
	.globl	.conststr.90
.conststr.90:
	.asciz	"g"
	.size	.conststr.90, 2

	.type	.conststr.91,@object    # @.conststr.91
	.globl	.conststr.91
.conststr.91:
	.asciz	"h"
	.size	.conststr.91, 2

	.type	.conststr.92,@object    # @.conststr.92
	.globl	.conststr.92
.conststr.92:
	.asciz	"i"
	.size	.conststr.92, 2

	.type	.conststr.93,@object    # @.conststr.93
	.globl	.conststr.93
.conststr.93:
	.asciz	"j"
	.size	.conststr.93, 2

	.type	.conststr.94,@object    # @.conststr.94
	.globl	.conststr.94
.conststr.94:
	.asciz	"k"
	.size	.conststr.94, 2

	.type	.conststr.95,@object    # @.conststr.95
	.globl	.conststr.95
.conststr.95:
	.asciz	"l"
	.size	.conststr.95, 2

	.type	.conststr.96,@object    # @.conststr.96
	.globl	.conststr.96
.conststr.96:
	.asciz	"m"
	.size	.conststr.96, 2

	.type	.conststr.97,@object    # @.conststr.97
	.globl	.conststr.97
.conststr.97:
	.asciz	"n"
	.size	.conststr.97, 2

	.type	.conststr.98,@object    # @.conststr.98
	.globl	.conststr.98
.conststr.98:
	.asciz	"o"
	.size	.conststr.98, 2

	.type	.conststr.99,@object    # @.conststr.99
	.globl	.conststr.99
.conststr.99:
	.asciz	"p"
	.size	.conststr.99, 2

	.type	.conststr.100,@object   # @.conststr.100
	.globl	.conststr.100
.conststr.100:
	.asciz	"q"
	.size	.conststr.100, 2

	.type	.conststr.101,@object   # @.conststr.101
	.globl	.conststr.101
.conststr.101:
	.asciz	"r"
	.size	.conststr.101, 2

	.type	.conststr.102,@object   # @.conststr.102
	.globl	.conststr.102
.conststr.102:
	.asciz	"s"
	.size	.conststr.102, 2

	.type	.conststr.103,@object   # @.conststr.103
	.globl	.conststr.103
.conststr.103:
	.asciz	"t"
	.size	.conststr.103, 2

	.type	.conststr.104,@object   # @.conststr.104
	.globl	.conststr.104
.conststr.104:
	.asciz	"u"
	.size	.conststr.104, 2

	.type	.conststr.105,@object   # @.conststr.105
	.globl	.conststr.105
.conststr.105:
	.asciz	"v"
	.size	.conststr.105, 2

	.type	.conststr.106,@object   # @.conststr.106
	.globl	.conststr.106
.conststr.106:
	.asciz	"w"
	.size	.conststr.106, 2

	.type	.conststr.107,@object   # @.conststr.107
	.globl	.conststr.107
.conststr.107:
	.asciz	"x"
	.size	.conststr.107, 2

	.type	.conststr.108,@object   # @.conststr.108
	.globl	.conststr.108
.conststr.108:
	.asciz	"y"
	.size	.conststr.108, 2

	.type	.conststr.109,@object   # @.conststr.109
	.globl	.conststr.109
.conststr.109:
	.asciz	"z"
	.size	.conststr.109, 2

	.type	.conststr.110,@object   # @.conststr.110
	.globl	.conststr.110
.conststr.110:
	.asciz	"{"
	.size	.conststr.110, 2

	.type	.conststr.111,@object   # @.conststr.111
	.globl	.conststr.111
.conststr.111:
	.asciz	"|"
	.size	.conststr.111, 2

	.type	.conststr.112,@object   # @.conststr.112
	.globl	.conststr.112
.conststr.112:
	.asciz	"}"
	.size	.conststr.112, 2

	.type	.conststr.113,@object   # @.conststr.113
	.globl	.conststr.113
.conststr.113:
	.asciz	"~"
	.size	.conststr.113, 2

	.type	.conststr.114,@object   # @.conststr.114
	.globl	.conststr.114
.conststr.114:
	.asciz	"int main(){int i=0;// Quine is a a program that produces its source code as output."
	.size	.conststr.114, 84

	.type	.conststr.115,@object   # @.conststr.115
	.globl	.conststr.115
.conststr.115:
	.asciz	"println(c[81]+c[82]+c[80]+c[71]+c[76]+c[69]+c[0]+c[66]+c[71]+c[69]+c[82]+c[7]+c[71]+c[76]+c[82]+c[0]+c[86]+c[8]+c[89]);"
	.size	.conststr.115, 120

	.type	.conststr.116,@object   # @.conststr.116
	.globl	.conststr.116
.conststr.116:
	.asciz	"println(c[71]+c[68]+c[7]+c[86]+c[28]+c[28]+c[15]+c[8]+c[80]+c[67]+c[82]+c[83]+c[80]+c[76]+c[0]+a2q+c[15]+a2q+c[26]);"
	.size	.conststr.116, 117

	.type	.conststr.117,@object   # @.conststr.117
	.globl	.conststr.117
.conststr.117:
	.asciz	"println(c[71]+c[68]+c[7]+c[86]+c[28]+c[28]+c[16]+c[8]+c[80]+c[67]+c[82]+c[83]+c[80]+c[76]+c[0]+a2q+c[16]+a2q+c[26]);"
	.size	.conststr.117, 117

	.type	.conststr.118,@object   # @.conststr.118
	.globl	.conststr.118
.conststr.118:
	.asciz	"println(c[71]+c[68]+c[7]+c[86]+c[28]+c[28]+c[17]+c[8]+c[80]+c[67]+c[82]+c[83]+c[80]+c[76]+c[0]+a2q+c[17]+a2q+c[26]);"
	.size	.conststr.118, 117

	.type	.conststr.119,@object   # @.conststr.119
	.globl	.conststr.119
.conststr.119:
	.asciz	"println(c[71]+c[68]+c[7]+c[86]+c[28]+c[28]+c[18]+c[8]+c[80]+c[67]+c[82]+c[83]+c[80]+c[76]+c[0]+a2q+c[18]+a2q+c[26]);"
	.size	.conststr.119, 117

	.type	.conststr.120,@object   # @.conststr.120
	.globl	.conststr.120
.conststr.120:
	.asciz	"println(c[71]+c[68]+c[7]+c[86]+c[28]+c[28]+c[19]+c[8]+c[80]+c[67]+c[82]+c[83]+c[80]+c[76]+c[0]+a2q+c[19]+a2q+c[26]);"
	.size	.conststr.120, 117

	.type	.conststr.121,@object   # @.conststr.121
	.globl	.conststr.121
.conststr.121:
	.asciz	"println(c[71]+c[68]+c[7]+c[86]+c[28]+c[28]+c[20]+c[8]+c[80]+c[67]+c[82]+c[83]+c[80]+c[76]+c[0]+a2q+c[20]+a2q+c[26]);"
	.size	.conststr.121, 117

	.type	.conststr.122,@object   # @.conststr.122
	.globl	.conststr.122
.conststr.122:
	.asciz	"println(c[71]+c[68]+c[7]+c[86]+c[28]+c[28]+c[21]+c[8]+c[80]+c[67]+c[82]+c[83]+c[80]+c[76]+c[0]+a2q+c[21]+a2q+c[26]);"
	.size	.conststr.122, 117

	.type	.conststr.123,@object   # @.conststr.123
	.globl	.conststr.123
.conststr.123:
	.asciz	"println(c[71]+c[68]+c[7]+c[86]+c[28]+c[28]+c[22]+c[8]+c[80]+c[67]+c[82]+c[83]+c[80]+c[76]+c[0]+a2q+c[22]+a2q+c[26]);"
	.size	.conststr.123, 117

	.type	.conststr.124,@object   # @.conststr.124
	.globl	.conststr.124
.conststr.124:
	.asciz	"println(c[71]+c[68]+c[7]+c[86]+c[28]+c[28]+c[23]+c[8]+c[80]+c[67]+c[82]+c[83]+c[80]+c[76]+c[0]+a2q+c[23]+a2q+c[26]);"
	.size	.conststr.124, 117

	.type	.conststr.125,@object   # @.conststr.125
	.globl	.conststr.125
.conststr.125:
	.asciz	"println(c[71]+c[68]+c[7]+c[86]+c[28]+c[28]+c[24]+c[8]+c[80]+c[67]+c[82]+c[83]+c[80]+c[76]+c[0]+a2q+c[24]+a2q+c[26]);"
	.size	.conststr.125, 117

	.type	.conststr.126,@object   # @.conststr.126
	.globl	.conststr.126
.conststr.126:
	.asciz	"println(c[91]);"
	.size	.conststr.126, 16

	.type	.conststr.127,@object   # @.conststr.127
	.globl	.conststr.127
.conststr.127:
	.asciz	"println(c[81]+c[82]+c[80]+c[71]+c[76]+c[69]+c[58]+c[59]+c[0]+c[81]+c[28]+c[76]+c[67]+c[85]+c[0]+c[81]+c[82]+c[80]+c[71]+c[76]+c[69]+c[58]+c[17]+c[20]+c[21]+c[59]+c[26]);"
	.size	.conststr.127, 170

	.type	.conststr.128,@object   # @.conststr.128
	.globl	.conststr.128
.conststr.128:
	.asciz	"println(c[81]+c[82]+c[80]+c[71]+c[76]+c[69]+c[58]+c[59]+c[0]+c[65]+c[28]+c[76]+c[67]+c[85]+c[0]+c[81]+c[82]+c[80]+c[71]+c[76]+c[69]+c[58]+c[17]+c[20]+c[21]+c[59]+c[26]);"
	.size	.conststr.128, 170

	.type	.conststr.129,@object   # @.conststr.129
	.globl	.conststr.129
.conststr.129:
	.asciz	"println(c[81]+c[82]+c[80]+c[71]+c[76]+c[69]+c[0]+c[81]+c[17]+c[7]+c[71]+c[76]+c[82]+c[0]+c[81]+c[81]+c[8]+c[89]);"
	.size	.conststr.129, 114

	.type	.conststr.130,@object   # @.conststr.130
	.globl	.conststr.130
.conststr.130:
	.asciz	"println(c[71]+c[68]+c[7]+c[81]+c[81]+c[27]+c[28]+c[24]+c[8]+c[80]+c[67]+c[82]+c[83]+c[80]+c[76]+c[0]+a2q+c[81]+c[58]+a2q+c[10]+c[66]+c[71]+c[69]+c[82]+c[7]+c[81]+c[81]+c[8]+c[10]+a2q+c[59]+c[28]+a2q+c[26]);"
	.size	.conststr.130, 207

	.type	.conststr.131,@object   # @.conststr.131
	.globl	.conststr.131
.conststr.131:
	.asciz	"println(c[80]+c[67]+c[82]+c[83]+c[80]+c[76]+c[0]+a2q+c[81]+c[58]+a2q+c[10]+c[66]+c[71]+c[69]+c[82]+c[7]+c[81]+c[81]+c[14]+c[16]+c[15]+c[8]+c[10]+c[66]+c[71]+c[69]+c[82]+c[7]+c[81]+c[81]+c[4]+c[16]+c[15]+c[8]+c[10]+a2q+c[59]+c[28]+a2q+c[26]);"
	.size	.conststr.131, 242

	.type	.conststr.132,@object   # @.conststr.132
	.globl	.conststr.132
.conststr.132:
	.asciz	"println(c[91]);"
	.size	.conststr.132, 16

	.type	.conststr.133,@object   # @.conststr.133
	.globl	.conststr.133
.conststr.133:
	.asciz	"println(c[81]+c[82]+c[80]+c[71]+c[76]+c[69]+c[0]+c[65]+c[17]+c[7]+c[71]+c[76]+c[82]+c[0]+c[65]+c[65]+c[8]+c[89]);"
	.size	.conststr.133, 114

	.type	.conststr.134,@object   # @.conststr.134
	.globl	.conststr.134
.conststr.134:
	.asciz	"println(c[71]+c[68]+c[7]+c[65]+c[65]+c[27]+c[28]+c[24]+c[8]+c[80]+c[67]+c[82]+c[83]+c[80]+c[76]+c[0]+a2q+c[65]+c[58]+a2q+c[10]+c[66]+c[71]+c[69]+c[82]+c[7]+c[65]+c[65]+c[8]+c[10]+a2q+c[59]+c[28]+a2q+c[26]);"
	.size	.conststr.134, 207

	.type	.conststr.135,@object   # @.conststr.135
	.globl	.conststr.135
.conststr.135:
	.asciz	"println(c[80]+c[67]+c[82]+c[83]+c[80]+c[76]+c[0]+a2q+c[65]+c[58]+a2q+c[10]+c[66]+c[71]+c[69]+c[82]+c[7]+c[65]+c[65]+c[14]+c[16]+c[15]+c[8]+c[10]+c[66]+c[71]+c[69]+c[82]+c[7]+c[65]+c[65]+c[4]+c[16]+c[15]+c[8]+c[10]+a2q+c[59]+c[28]+a2q+c[26]);"
	.size	.conststr.135, 242

	.type	.conststr.136,@object   # @.conststr.136
	.globl	.conststr.136
.conststr.136:
	.asciz	"println(c[91]);"
	.size	.conststr.136, 16

	.type	.conststr.137,@object   # @.conststr.137
	.globl	.conststr.137
.conststr.137:
	.asciz	"println(c[81]+c[82]+c[80]+c[71]+c[76]+c[69]+c[0]+c[65]+c[77]+c[28]+a2q+c[26]+a2q+c[26]);"
	.size	.conststr.137, 89

	.type	.conststr.138,@object   # @.conststr.138
	.globl	.conststr.138
.conststr.138:
	.asciz	"println(c[81]+c[82]+c[80]+c[71]+c[76]+c[69]+c[0]+c[63]+c[17]+c[79]+c[28]+a2q+a2b+a2q+a2q+c[26]);"
	.size	.conststr.138, 97

	.type	.conststr.139,@object   # @.conststr.139
	.globl	.conststr.139
.conststr.139:
	.asciz	"println(c[81]+c[82]+c[80]+c[71]+c[76]+c[69]+c[0]+c[63]+c[17]+c[64]+c[28]+a2q+a2b+a2b+a2q+c[26]);"
	.size	.conststr.139, 97

	.type	.conststr.140,@object   # @.conststr.140
	.globl	.conststr.140
.conststr.140:
	.asciz	"println(s[0]);"
	.size	.conststr.140, 15

	.type	.conststr.141,@object   # @.conststr.141
	.globl	.conststr.141
.conststr.141:
	.asciz	"for(i=0;i<93;i++)println(c2(i)+a2q+c[i]+a2q+co);"
	.size	.conststr.141, 49

	.type	.conststr.142,@object   # @.conststr.142
	.globl	.conststr.142
.conststr.142:
	.asciz	"for(i=0;i<32;i++)println(s2(i)+a2q+s[i]+a2q+co);"
	.size	.conststr.142, 49

	.type	.conststr.143,@object   # @.conststr.143
	.globl	.conststr.143
.conststr.143:
	.asciz	"for(i=1;i<32;i++)println(s[i]);"
	.size	.conststr.143, 32

	.type	.conststr.144,@object   # @.conststr.144
	.globl	.conststr.144
.conststr.144:
	.asciz	"return 0;"
	.size	.conststr.144, 10

	.type	.conststr.145,@object   # @.conststr.145
	.globl	.conststr.145
.conststr.145:
	.asciz	"}"
	.size	.conststr.145, 2

	.type	.L.str,@object          # @.str
	.section	.rodata.str1.1,"aMS",@progbits,1
.L.str:
	.asciz	"%s"
	.size	.L.str, 3

	.type	.L.str.1,@object        # @.str.1
.L.str.1:
	.asciz	"%s\n"
	.size	.L.str.1, 4

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

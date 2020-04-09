import subprocess
import os

def getInput(filename):
        caseData = open(filename, 'r').readlines()
        caseData = [i.strip('\n') for i in caseData]
        metaIdx = (caseData.index('/*'), caseData.index('*/'))
        metaArea = caseData[metaIdx[0] + 1: metaIdx[1]]
        metaConfigArea = [i for i in metaArea if '===' not in i and 'output' not in i and 'Input' not in i and 'Output' not in i]
        metaConfigArea = [i.split(': ') for i in metaConfigArea]
        newMetaArea = metaArea[metaArea.index('=== end ===') + 1:]
        inputDataStr = '\n'.join(metaArea[metaArea.index('=== input ===') + 1 : metaArea.index('=== end ===')])
        outputDataStr = '\n'.join(newMetaArea[newMetaArea.index('=== output ===') + 1 : newMetaArea.index('=== end ===')])
        
        input = open("test.in", "w")
        input.write(inputDataStr)

        output = open("ans.out", "w")
        output.write(outputDataStr)

def test(filename, input = "test.in", output = "ans.out"):
    print("Testing %s" % filename)
    if input == "test.in":
        getInput(filename + '.mx')
    os.system("./build.sh >/dev/null 2>&1")
    os.system("cat " + filename + '.mx' + " > " + "code.mx")
    os.system("./semantic.sh >/dev/null 2>&1")
    os.system("llvm-link mycode.ll builtin.ll string_builtin.ll string_utility.ll -o code.ll; llc code.ll -o code.s; clang code.s -o code")
    os.system("./code < " + input + " > my.out")
    os.system("diff -wBEZb " + output + " my.out")
    if os.system("diff -wBEZb " + output + " my.out > /dev/null 2>&1"):
        print("WRONG")
        return 0
    else:
        print("PASS")
        return 1

with open("./testcases/codegen/judgelist.txt", "r") as f:
    l = f.readlines()
ans = 0
for i in l:
    ans += test("./testcases/codegen/" + i[2:-4])
print("PASSED %d out of %d" % (ans, len(l)))
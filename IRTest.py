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

def test(filename):
    print("Testing %s" % filename)
    getInput(filename + '.mx')
    os.system("./build.sh >/dev/null 2>&1")
    os.system("cat " + filename + '.mx' + " > " + "code.mx")
    os.system("./semantic.sh >/dev/null")
    os.system("llvm-link mycode.ll builtin.ll string_builtin.ll string_utility.ll -o code.ll")
    os.system("lli code.ll < test.in > my.out")
    os.system("diff -w ans.out my.out | head")
    if os.system("diff -w ans.out my.out > /dev/null 2>&1"):
        print("WRONG")
    else:
        print("PASS")

# for i in range(1, 11):
    # test("./testcases/codegen/e" + str(i))

for i in range(10,11):
    test("./testcases/codegen/t" + str(i))
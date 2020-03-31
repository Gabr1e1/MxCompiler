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
    os.system("./build.bash >/dev/null 2>&1")
    os.system("cat " + filename + '.mx' + " > " + "code.mx")
    os.system("./semantic.bash >/dev/null 2>&1")
    os.system("llvm-link mycode.ll builtin.ll string_builtin.ll string_utility.ll -o code.ll; llc code.ll -o code.s; clang code.s -o code")
    os.system("./code < " + input + " > my.out; truncate -s -1 my.out")
    os.system("diff -w " + output + " my.out | head")
    if os.system("diff -w " + output + " my.out | head > /dev/null 2>&1"):
        print("WRONG")
    else:
        print("PASS")

# for i in range(1, 11):
    # test("./testcases/codegen/e" + str(i))


# 2: lacking init of everything
# 22: return type of printNum()
# 24: no return statement in search(), no init of color[]
# 31: no return statement in prime() & printF()

# still wrong: 65

# for i in range(2,3):
#     test("./testcases/codegen/t" + str(i))

# with open("./testcases/codegen/judgelist.txt", "r") as f:
#     l = f.readlines()
# for i in l:
#     test("./testcases/codegen/" + i[2:-4])

test("./testcases/codegen2/shortest_path/spfa", "./testcases/codegen2/shortest_path/shortest_path.0.in", "./testcases/codegen2/shortest_path/shortest_path.0.ans")

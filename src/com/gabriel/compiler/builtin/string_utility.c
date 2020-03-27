#include <stdlib.h>
#include <string.h>

char* _string_concatenate(char *a, char *b) {
    int la = strlen(a), lb = strlen(b);
    char *ret = (char*) malloc(la + lb + 1);
    for (int i = 0; i < la; i++) ret[i] = a[i];
    for (int i = 0; i < lb; i++) ret[i + la] = b[i];
    ret[la + lb] = '\0';
    return ret;
}

char _string_eq(char *a, char *b) {
    return strcmp(a, b) == 0;
}

char _string_ne(char *a, char *b) {
    return strcmp(a, b) != 0;
}

char _string_slt(char *a, char *b) {
    return strcmp(a, b) < 0;
}

char _string_sle(char *a, char *b) {
    return strcmp(a, b) <= 0;
}

char _string_sgt(char *a, char *b) {
    return strcmp(a, b) > 0;
}

char _string_sge(char *a, char *b) {
    return strcmp(a, b) >= 0;
}

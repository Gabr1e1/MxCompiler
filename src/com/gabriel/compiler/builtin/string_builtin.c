#include <string.h>
#include <stdlib.h>

int _string_length(char *this) {
    return strlen(this);
}

char* _string_substring(char *this, int left, int right) {
    char* ret = (char*) malloc(sizeof(char) * (right - left + 1));
    for (int i = left; i < right; i++) ret[i - left] = this[i];
    ret[right - left] = '\0';
    return ret;
}

int _string_parseInt(char *this) {
    int ret = 0, sgn = 1, cnt = 0;
    if (this[0] == '-') sgn = -1, cnt = 1;
    while (this[cnt] >= '0' && this[cnt] <= '9') 
        ret = ret * 10 + this[cnt++];
    return ret * sgn;
}

int _string_ord(char *this, int pos) {
    return this[pos];
}
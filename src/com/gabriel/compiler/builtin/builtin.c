#include <stdio.h>
#include <stdlib.h>

void print(char *str) {
    printf("%s", str);
}

void println(char *str) {
    printf("%s\n", str);
}

void printInt(int num) {
    printf("%d", num);
}

void printlnInt(int num) {
    printf("%d\n", num);
}

char* toString(int n) {
    int m = n;
    int cnt = n < 0;
    n = n < 0 ? -n : n;
    while (n) {
        n /= 10;
        cnt++;
    }
    char *str = (char*) malloc((cnt + 1) * sizeof(char));

    if (m < 0) str[0] = '-';
    m = m < 0 ? -m : m;
    str[cnt--] = '\0';
    while (m) {
        str[cnt--] = m % 10 + '0';
        m /= 10;
    }
    return str;
}

int getInt() {
    int ret;
    scanf("%d", &ret);
    return ret;
}

char* getString() {
    char* str = (char*) malloc(256);
    scanf("%s", str);
    return str;
}
int fun(int x){
    if (x <= 1) return x;
    else return fun(x - 1);
}

int main() {
    return fun(10);
}
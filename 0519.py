N, M=map(int, input().split())
maps=[list(map(int, input().split())) for _ in range(N)]
maxmap=[[-1]*M for _ in range(N)]
maxmap[N-1][0]=0

for i in range(1,M):
    if maps[N-1][i] == 2:
        for j in range(i, M):
            maps[]
    maxmap[N-1][i] = maxmap[N-1][i-1] + maps[N-1][i]

for i in range(N-2,-1,-1):
    for j in range(M):
        if j == 0:
            maxmap[i][j] = maps[i][j] + maxmap[i+1][j]
        else:
            maxmap[i][j] = maps[i][j] + max(maxmap[i][j-1], maxmap[i+1][j], 0)
print(maxmap[0][M-1])
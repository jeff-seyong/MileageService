# Mileage Service Demo

## Getting Start!

```bash
$ git clone https://github.com/jeff-seyong/MileageService.git
$ cd MileageService
$ docker-compose up -d
$ ./gradlew build
# '-bash: ./gradlew: Permission denied' 에러 시
# `$ chmod +x gradlew` 명령어 입력 후 rebuild
$ java -jar build/libs/MileageService-0.0.1-SNAPSHOT.jar
```


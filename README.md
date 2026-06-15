# 🍪 두쫀쿠 (Dujjonku) - Backend Server

> **따라가기 벅찬 요즘 유행어, 한눈에 쉽게**
> 세대 간 언어 장벽을 허물기 위한 유행어 사전 서비스 '두쫀쿠'의 백엔드(Spring Boot) 레포지토리입니다.


---
⚡ 실행 순서 요약 (Quick Start)
처음 세팅하시는 분은 아래 순서대로 진행하면 됩니다.



1. **사전 준비** — Docker Desktop, JDK 17 이상 설치 및 실행
2. **레포지토리 클론** — `git clone` 후 프로젝트 폴더로 이동
3. **.env 파일 생성** — 최상단(`core/.env`)에 `MYSQL_PASSWORD`, `API_KEY` 설정
4. **MySQL 컨테이너 실행** — `docker-compose up -d`
5. **DB 초기 세팅** — DB 클라이언트로 DDL/DML 쿼리 실행 (테이블 생성 + 퀴즈 더미)
6. **백엔드 서버 실행** — `./gradlew build` 후 `./gradlew bootRun`
7. **크롤링 더미 데이터 주입** — Postman으로 `POST http://localhost:8080/crawling`에 JSON 전송

💡 각 단계의 상세 내용은 아래 섹션을 참고하세요.



---

## 사전 준비 사항 (Prerequisites)

로컬에서 서버를 실행하기 전에 아래 항목이 설치되어 있어야 합니다.

- **Docker Desktop** : MySQL 컨테이너 구동을 위해 반드시 사전에 설치 및 실행되어 있어야 합니다.
- **JDK 17 이상** : Spring Boot 서버 빌드 및 실행에 필요합니다.

> ⚠️ Docker가 설치/실행되어 있지 않으면 MySQL 컨테이너가 뜨지 않아 서버 연동이 불가능합니다.

---

## 환경 변수(.env) 설정

프로젝트 최상단 경로(`core/`)에 `.env` 파일을 생성하고 아래 내용을 입력합니다.

```
MYSQL_PASSWORD=root
API_KEY={제미나이 api키를 발급받아 작성}
```

> 💡 `.env` 파일은 반드시 최상단(`core/.env`) 경로에 위치해야 정상적으로 인식됩니다.

---

## 로컬 서버 실행 방법 (How to Run)

### 1. 레포지토리 클론 및 빌드
```bash
git clone https://github.com/chc216/dujjonku-core-api.git

cd dujjonku-core-api
```
### 2. 도커(Docker) 컨테이너 실행
먼저 Docker Desktop을 실행한 후, 아래 명령어로 MySQL 컨테이너를 구동합니다.

참고 : MySQL 컨테이너는 로컬 3306 포트를 사용합니다.
```bash
docker-compose up -d
```
### 3. 백엔드 서버 실행
```bash
./gradlew build

./gradlew bootRun
```
서버는 기본적으로 http://localhost:8080 포트에서 실행됩니다.


## 데이터베이스(MySQL) 초기 세팅 안내

본 백엔드 서버는 프론트엔드 랜딩 페이지의 **'미니 퀴즈 조회(Retrieve)'**   **'알림 구독 삽입(Insert)'**   **'랭킹 대시보드 및 단어 카드'** 기능을 지원하기 위해 아래의 DB 테이블과 초기 데이터가 필요합니다.

로컬 환경에서 프론트엔드와 정상적으로 연동하기 위해, 사용하시는 DB 클라이언트 도구(MySQL Workbench, DBeaver, IntelliJ Database 등)를 통해 아래의 DDL 및 DML 쿼리를 먼저 실행해 주시기 바랍니다.

> 💡 **참고 (퀴즈 랜덤 출제 시스템):**  랜딩 페이지에서는 미니 테스트용으로 1문제만 렌더링되지만, 실제 퀴즈 페이지로 진입 시 전체 퀴즈 풀에서 10문제를 랜덤으로 추출하여 제공합니다.
<details>
<summary><b>클릭해서 DB 세팅 쿼리 보기</b></summary>
<div markdown="1">

```sql
-- 데이터베이스 core_db 지정 필요 (use core_db)


-- 기존 테이블이 존재할 경우 안전하게 삭제 (초기화용)
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS quiz;
DROP TABLE IF EXISTS subscription;
DROP TABLE IF EXISTS todayword;
DROP TABLE IF EXISTS word_frequency;
DROP TABLE IF EXISTS ranking;
DROP TABLE IF EXISTS admin;
DROP TABLE IF EXISTS today_word;
SET FOREIGN_KEY_CHECKS = 1;

-- 1. 알림 구독자(Subscription) 테이블 생성
CREATE TABLE subscription (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    consent BOOLEAN NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 2. 퀴즈(Quiz) 테이블 생성
CREATE TABLE quiz (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    word_id BIGINT NOT NULL,
    admin_id BIGINT NOT NULL,
    question VARCHAR(255) NOT NULL,
    answer_num INT NOT NULL,
    option1 VARCHAR(255) NOT NULL,
    option2 VARCHAR(255) NOT NULL,
    option3 VARCHAR(255) NOT NULL,
    option4 VARCHAR(255) NOT NULL,
    explanation TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted_at DATETIME
);

-- 3. 단어(Word) 테이블 생성
CREATE TABLE word (
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    name          VARCHAR(255) DEFAULT NULL,
    meaning       VARCHAR(255) DEFAULT NULL,
    example       VARCHAR(255) DEFAULT NULL,
    scenario      VARCHAR(255) DEFAULT NULL,
    created_at    DATETIME     DEFAULT CURRENT_TIMESTAMP,
    like_count    BIGINT       NOT NULL DEFAULT 0,
    dislike_count BIGINT       NOT NULL DEFAULT 0,
    PRIMARY KEY (id)
);

-- 4. 단어 빈도(Word Frequency) 테이블 생성
CREATE TABLE word_frequency (
    id          BIGINT NOT NULL AUTO_INCREMENT,
    word_id     BIGINT NOT NULL,
    record_date DATE   NOT NULL,
    frequency   INT    DEFAULT 0,
    PRIMARY KEY (id)
);

-- 5. 랭킹(Ranking) 테이블 생성
CREATE TABLE ranking (
    id            BIGINT       NOT NULL AUTO_INCREMENT,
    word_id       BIGINT       NOT NULL,
    name          VARCHAR(255) NOT NULL,
    meaning       TEXT,
    example       TEXT,
    trend         VARCHAR(255) DEFAULT NULL,
    ranking_order INT          NOT NULL,
    target_date   DATE         NOT NULL,
    PRIMARY KEY (id)
);

-- 6. 오늘의 단어(Todayword) 테이블 생성
CREATE TABLE todayword (
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    word_id BIGINT       DEFAULT NULL,
    name    VARCHAR(255) DEFAULT NULL,
    meaning VARCHAR(255) DEFAULT NULL,
    example VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id)
);

--7. 알림발송용 오늘의 단어 테이블 생성
CREATE TABLE today_word (
    id           BIGINT NOT NULL AUTO_INCREMENT,
    word_id      BIGINT DEFAULT NULL,
    display_date DATE   NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (word_id) REFERENCES word(id) ON DELETE CASCADE
);
--8. 관리자 테이블 생성
CREATE TABLE admin (
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    login_id VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name     VARCHAR(255)
);

-- 8. 퀴즈 더미 데이터 삽입 (20문제)
INSERT INTO quiz (word_id, admin_id, question, answer_num, option1, option2, option3, option4, explanation, created_at) VALUES 
(1, 1, '최근 젊은 세대 사이에서 "오히려 좋아"를 뜻하는 유행어는?', 2, '그저 빛', '럭키비키', '폼 미쳤다', '알잘딱깔센', '"원영적 사고"에서 파생된 말로, 긍정적 해석을 의미합니다.', NOW()),
(2, 1, '"중요한 것은 꺾이지 않는 마음"을 줄여서 부르는 말은?', 3, '중꺾마', '중마꺾', '중꺾마', '꺾중마', '2022년 롤드컵 데프트 선수의 인터뷰에서 유래했습니다.', NOW()),
(3, 1, '갑자기 분위기가 싸해지는 상황을 줄여서 부르는 말은?', 1, '갑분싸', '갑분띠', '갑분핫', '갑분노', '썰렁한 농담 등으로 주변 분위기가 어색해질 때 사용합니다.', NOW()),
(4, 1, '자신의 재력이나 귀중품을 과시하는 행위를 뜻하는 단어는?', 3, '스웨그', '욜로', '플렉스', '드롭', '힙합 문화에서 유래한 과시 행위를 뜻합니다.', NOW()),
(5, 1, '"내 돈 주고 내가 산 것"을 줄여서 쓰는 말은?', 4, '내돈', '내가산', '협찬아님', '내돈내산', '협찬이 아닌 순수한 솔직 리뷰임을 인증할 때 씁니다.', NOW()),
(6, 1, '"알아서 잘 딱 깔끔하고 센스있게"의 줄임말은?', 2, '알잘센', '알잘딱깔센', '알딱센', '알잘깔딱', '우왁굳 방송에서 유래하여 널리 쓰이는 신조어입니다.', NOW()),
(7, 1, '타인의 말에 공감하지 못하고 이성적으로만 반응할 때 묻는 말은?', 1, '너 T야?', '너 F야?', '너 J야?', '너 P야?', 'MBTI 성격 유형 중 사고형(T)의 특징을 빗댄 말입니다.', NOW()),
(8, 1, '"오늘 운동 완료"의 줄임말은?', 3, '오운끝', '오운다', '오운완', '오운성', 'SNS에서 운동을 인증할 때 해시태그로 자주 사용됩니다.', NOW()),
(9, 1, '스스로 불러온 재앙을 뜻하는 단어는?', 4, '스불행', '스불재앙', '스불망', '스불재', '자신의 선택으로 인해 겪게 되는 고통을 자조적으로 이르는 말입니다.', NOW()),
(10, 1, '동의한다는 의미로 사용하는 걸그룹 뉴진스의 노래 제목은?', 1, '디토(Ditto)', '하입보이', '오엠지(OMG)', '어텐션', '"나도 동의해"라는 뜻의 영단어에서 유래했습니다.', NOW()),
(11, 1, '억지로 텐션을 끌어올리는 모습을 뜻하는 말은?', 2, '억끌', '억텐', '찐텐', '노텐', '진짜 텐션(찐텐)의 반대말로 억지로 신난 척할 때 씁니다.', NOW()),
(12, 1, '분위기 좋은 카페를 줄여서 부르는 말은?', 3, '분카', '분좋', '분좋카', '분카페', '인스타그램 감성이 충만한 카페를 지칭할 때 사용합니다.', NOW()),
(13, 1, '"점심 메뉴 추천"의 줄임말은?', 2, '점메선', '점메추', '점메픽', '점추', '결정장애가 올 때 친구들에게 점심 메뉴를 물어볼 때 씁니다.', NOW()),
(14, 1, '완전 내 스타일이라는 뜻의 유행어는?', 4, '내스완', '완스', '완내픽', '완내스', '어떤 것이 자신의 취향에 완벽하게 부합할 때 사용합니다.', NOW()),
(15, 1, '열받네를 강조하여 부르는 신조어는?', 1, '킹받네', '갓받네', '짱받네', '핵받네', '왕(King)과 열받네를 합성하여 아주 화가 난다는 뜻입니다.', NOW()),
(16, 1, '내가 추구하는 이미지나 방향성을 뜻하는 말은?', 2, '원픽', '추구미', '워너비', '롤모델', '자신이 지향하는 아름다움이나 스타일을 의미합니다.', NOW()),
(17, 1, '아주 뛰어나거나 멋진 상태를 표현할 때 "ㅇ 미쳤다"에 들어갈 말은?', 3, '각', '선', '폼', '결', '어떤 사람의 기량이나 상태가 최고조에 달했을 때 씁니다.', NOW()),
(18, 1, '갓생(God+인생)의 반대말로 잉여롭게 보내는 삶은?', 1, '혐생', '노생', '망생', '빈생', '현실 생활이 힘들고 싫을 때 자조적으로 부르는 말입니다.', NOW()),
(19, 1, '무엇이든 할 수 있다는 긍정적인 의미의 "중꺾마" 변형은?', 4, '중마', '중마꺾', '꺾지마', '중꺾그마', '"중요한 것은 꺾였는데도 그냥 하는 마음"이라는 변형 유행어입니다.', NOW()),
(20, 1, '엄마 카드, 아빠 카드를 줄여서 부르는 말은?', 2, '엄카아카', '엄카/아카', '마카파카', '부카', '부모님의 카드로 결제할 때 흔히 사용하는 말입니다.', NOW());


-- 오늘의 단어(Todayword) 더미 데이터 삽입 (5개)
INSERT INTO todayword (word_id, name, meaning, example) VALUES
(1, '럭키비키', '아이브 장원영의 사고방식에서 유래한 말로, 어떤 상황에서도 긍정적인 면을 찾아내려는 초긍정적인 태도.', '시험 망친 줄 알았는데 다 아는 문제 나왔어, 완전 럭키비키잖아!'),
(2, '추구미', '추구하다와 미(美)의 합성어로, 본인이 평소에 지향하거나 닮고 싶은 스타일·분위기·이미지.', '요즘 내 추구미는 약간 이런 시크한 느낌이야.'),
(3, '스불재', '''스스로 불러온 재앙''의 줄임말로, 자신이 벌인 일 때문에 스스로 곤란해지는 상황.', '일정 너무 무리해서 잡았나... 또 스불재 겪는 중.'),
(6, '갓생', '신(God)과 인생의 합성어로, 부지런하고 생산적이며 모범이 될 만한 성실한 삶.', '내일부터 아침 6시에 일어나서 갓생 산다 진짜.'),
(9, '캘박', '''캘린더 박제''의 줄임말로, 약속이나 일정을 잊지 않도록 캘린더에 미리 입력해 고정하는 행위.', '우리 다음 모임은 15일로 캘박하자!');

-- 관리자 테이블 데이터 삽입
INSERT INTO admin (login_id, password, name) VALUES
('admin01', '1234', '최현철'),
('admin02', '1234', '박재광'),
('admin03', '1234', '김진영');

-- 구독자 테이블 임시 데이터 삽입
INSERT INTO subscription (email, consent) VALUES
('dujjonku@cbnu.ac.kr', true),
('hello_trend@example.com', true);

-- 알림 발송용 오늘의 단어 참조 데이터 삽입
INSERT INTO today_word (word_id, display_date) VALUES
(3, '2026-06-15');

```

</div>
</details>

---

## 더미 데이터 주입 안내 (크롤링 데이터)

크롤링 서버는 현재 개발 중인 관계로, 단어/랭킹/오늘의 단어 등 나머지 기능을 정상적으로 사용하려면 더미 데이터를 수동으로 주입해야 합니다.

**[Postman](https://www.postman.com/)** 등의 API 도구를 이용해 아래와 같이 요청을 보내주세요.

- **Method** : `POST`
- **URL** : `http://localhost:8080/crawling`
- **Header** : `Content-Type: application/json`
- **Body (raw / JSON)** : 아래 JSON 데이터를 그대로 첨부

> ⚠️ 이 요청을 보내야 더미 데이터가 적재되어 단어 조회·랭킹·오늘의 단어 등의 기능을 사용할 수 있습니다.

<details>
<summary><b>클릭해서 크롤링 더미 데이터(JSON) 보기</b></summary>
<div markdown="1">

```json
[
  {
    "keyword": "럭키비키",
    "platformFrequencies": {
      "twitter": 4500,
      "instagram": 3200,
      "youtube": 2800,
      "community": 1500
    },
    "originalExamples": [
      "오늘 비 와서 시원하니까 완전 럭키비키잖아!",
      "오히려 좋아! 럭키비키 마인드로 가자"
    ]
  },
  {
    "keyword": "추구미",
    "platformFrequencies": {
      "twitter": 3800,
      "instagram": 4100,
      "youtube": 1900,
      "community": 1200
    },
    "originalExamples": [
      "요즘 내 추구미는 약간 이런 시크한 느낌이야",
      "이 코디 완전 내 추구미 저격함"
    ]
  },
  {
    "keyword": "스불재",
    "platformFrequencies": {
      "twitter": 5200,
      "instagram": 800,
      "youtube": 1100,
      "community": 3400
    },
    "originalExamples": [
      "일정 너무 무리해서 잡았나... 또 스불재 겪는 중",
      "이번 과제는 완전 스불재야"
    ]
  },
  {
    "keyword": "분좋카",
    "platformFrequencies": {
      "twitter": 1200,
      "instagram": 5500,
      "youtube": 2100,
      "community": 1800
    },
    "originalExamples": [
      "주말에 친구랑 갈 연남동 분좋카 추천 좀!",
      "오랜만에 분좋카 가서 힐링하고 왔어"
    ]
  },
  {
    "keyword": "점메추",
    "platformFrequencies": {
      "twitter": 2900,
      "instagram": 1500,
      "youtube": 800,
      "community": 4200
    },
    "originalExamples": [
      "오늘 진짜 뭐 먹지? 점메추 받습니다",
      "비 오는 날엔 역시 짬뽕이지. 점메추 성공!"
    ]
  },
  {
    "keyword": "갓생",
    "platformFrequencies": {
      "twitter": 3100,
      "instagram": 4800,
      "youtube": 3500,
      "community": 2200
    },
    "originalExamples": [
      "내일부터 아침 6시에 일어나서 갓생 산다 진짜",
      "요즘 갓생 살기 너무 힘들어서 포기할까 고민 중"
    ]
  },
  {
    "keyword": "억까",
    "platformFrequencies": {
      "twitter": 2500,
      "instagram": 700,
      "youtube": 4600,
      "community": 5800
    },
    "originalExamples": [
      "이건 솔직히 너무 억까 아니냐?",
      "억까 좀 그만해라, 팩트만 놓고 얘기하자"
    ]
  },
  {
    "keyword": "너 T야",
    "platformFrequencies": {
      "twitter": 2200,
      "instagram": 2700,
      "youtube": 4100,
      "community": 3300
    },
    "originalExamples": [
      "거기서 공감을 안 해준다고? 너 T야?",
      "이 상황에서 그런 팩트폭력... 혹시 너 T야?"
    ]
  },
  {
    "keyword": "캘박",
    "platformFrequencies": {
      "twitter": 3600,
      "instagram": 2100,
      "youtube": 500,
      "community": 1700
    },
    "originalExamples": [
      "우리 다음 모임은 15일로 캘박하자!",
      "일단 그 날짜로 캘박해두고 나중에 다시 얘기해"
    ]
  },
  {
    "keyword": "알빠노",
    "platformFrequencies": {
      "twitter": 1900,
      "instagram": 900,
      "youtube": 4300,
      "community": 6100
    },
    "originalExamples": [
      "내일 모레가 시험인데 알빠노?",
      "그 사람이 뭐라고 하든 내 알빠노"
    ]
  }
]
```

</div>
</details>

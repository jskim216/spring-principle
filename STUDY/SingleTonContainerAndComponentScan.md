## 스프링 핵심 원리 기본편

### 웹 애플리케이션과 싱글톤

- 웹 애플리케이션은 보통 여러 고객이 "동시에" 요청
- 요청이 올때마다 객체를 생성하게되면 메모리 낭비가 매우 심하다.
- 해결방안은 객체가 1개만 생성되고 서로 공유하도록 설계 > 싱글톤 패턴

### 싱글톤 패턴

- 클래스의 인스턴스가 딱 1개만 생성되는 것을 보장
- 객체 인스턴스를 2개이상 생성하지 못하도록!
- 생성자를 private 로 막아서 외부에서 객체 인스턴스가 생성되는 것을 막아야함

### 싱글톤 패턴의 문제점

- 구체클래스에 의존. DIP 위반
- OCP 원칙 위반 가능성이 높음
- 유연한 테스트가 어려움
- 내부 속성 변경, 초기화 여러움
- 유연성 떨어짐
- 안티패턴

### 스프링에서의 싱글톤 패턴

- 스프링 컨테이너가 싱글톤을 관리

### 스프링 컨테이너

- 싱글톤 패턴의 모든 문제점을 해결하며 객체 인스턴스를 싱글톤으로 관리
- 싱글톤 객체를 생성하고 관리하는 기능 > 싱글톤 레지스트리
- 스프링의 기본 빈 등록은 싱글톤. 거의 대부분은 싱글톤 등록 방식을 사용함

### 싱글톤 방식의 주의점

- 싱글톤 패턴 또는 싱글톤 컨테이너에서 하나의 같은 객체 인스턴스를 공유하기때문에 객체는 stateless 로 설계해야함
- 특정 클라이언트가 값을 변경할 수 있는 필드가 있어서는 안됨
- 공유필드는 매우 조심해야.. 스프링 빈은 항상 무상태(stateless) 로 설계!!
- 필드 대신에 공유되지 않는 지역변수 등을 사용

### @Configuration 과 싱글톤

- @Configuration 이 할당된 AppConfig 클래스
- 자바 코드상으로는 연달아 new 키워드로 객체를 생성하는데 스프링 컨테이너는 어떻게 싱글톤을 보장하는가!?
- CGLIB 라는 바이트 코드 조작 라이브러리를 통해 AppConfig 클래스를 상속받은 다른 클래스를 만들고 스프링 빈으로 등록
- 아마도 내부적으로는 @Bean 이 할당된 스프링 빈이 이미 존재하면 해당 빈을 반환하고 없으면 생성해서 스프링 빈으로 등록하는 로직이 존재
- 이로인해 싱글톤을 보장해줌

### @Configuration 을 할당하지 않으면?

- @Bean 은 스프링 빈으로 동일하게 등록하지만, 싱글톤은 보장하지 않아 각기 다른 객체 인스턴스로 생성
- 스프링 설정정보는 항상 @Configuration 을 사용하면됨.

### 컴포넌트 스캔과 의존관계 자동주입

### 컴포넌트 스캔

- 설정 정보 없이도 자동으로 스프링 빈을 등록하는 기능제공
- @Component 어노테이션이 할당된 클래스를 모두 찾아 빈으로 등록
- @Configuraion 이 할당된 config 클래스도 주입대상임 (Configuraion 에도 @Component 가 할당되어 있음)
- 스프링 빈의 기본 이름은 클래스명을 사용, 맨 앞글자만 소문자를 사용
- 빈 이름을 지정하고 싶다면, @Component("빈 이름") 으로 부여가능

### 컴포넌트 스캔 필터링

- excludeFilters 를 통해 제외할 클래스를 필터링 할 수 있음
- 일반적인 실무에서는 @Configuration 대상을 제외하지는 않으나 예제를 유지하기위해 사용했음

### 의존관계 자동주입

- @Component 만으로는 의존관계를 설정 할 수 없기에 @Autowired 를 통해 자동으로 주입
- 생성자에 @Autowired 를 지정시 스프링 컨테이너가 해당 스프링 빈의 타입을 기준으로 찾아서 주입

### 컴포넌트 스캔 위치 지정

- basePackages 옵션으로 패키지 시작위치를 지정
- basePackageClasses 옵션으로 지정한 클래시의 패키지를 시작위치
- 위치 지정 안하면? @ComponentScan 대상 클래스의 패키지를 시작위치

### 컴포넌트 스캔 위치 지정의 권장하는 방법

- 패키지 위치를 하지 않고, 설정 정보 클래스의 위치를 프로젝트 최상단에 두는 것을 권장

### 스프링 부트의 경우

- @SpringBootApplication 에 이미 @ComponentScan 이 존재

### 컴포넌트 스캔 기본대상

- 스캔 뿐만아니라 부가기능을 수행함
- @Component
- @Controller     스프링 MVC 컨트롤러로 인식
- @Repository     스프링 데이터 접근 계층으로 인식, 데이터 계층의 예외를 스프링 예외로 변환
- @Service        특별한 처리를 하지는 않음. 비즈니스 계층을 인식하는데 도움
- @Configuration  스프링 설정 정보로 인식


`어노테이션에는 상속관계라는 것이 없음. 어노테이션이 특정 어노테이션을 들고 있는 것을 인식할 수 있는 것은 스프링이 지원하는 기능`

### 필터

- includeFilters  컴포넌트 스캔 대상을 추가로 지정
- excludeFilters  컴포넌트 스캔에서 제외할 대상을 지정

### FilterType 옵션

- FilterType.ANNOTATION        기본값, 어노테이션을 인식
- FilterType.ASSIGNABLE_TYPE   지정한 타입과 자식타입을 인식
- FilterType.ASPECTJ           AspectJ 패턴 사용
- FilterType.REGEX             정규 표현식
- FilterType.CUSTOM            TypeFilter 인터페이스를 구현하여 처리

### 중복 등록과 충돌

### 자동 빈 등록 끼리의 충돌

- 컴포넌트 스캔에 의해 자동으로 등록된 빈의 이름이 같은 경우 예외 발생 > ConflictingBeanDefinitionException

### 수동 빈 과 자동 빈 등록의 충돌

- 수동 빈 등록이 우선권을 가짐(오버라이딩)
- 현실적으로는 의도적 설정이라기보다는 여러 설정들이 꼬여서 나타나는 경우가 많음 > 버그 발생 우려
- 최근 스프링 부트에서는 수동 빈 등록과 자동 빈 등록이 충돌이 나면 오류가 발생하도록 기본값을 바꿈 > overriding disabled
- 설정값 spring.main.allow-bean-definition-overriding=true
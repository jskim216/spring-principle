package hello.core.singleton;

import hello.core.member.MemberService;
import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class SingleTonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {

        AppConfig appConfig = new AppConfig();

        //1. 조회 : 호출할 때 마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();

        //2. 조회 : 호출할 떄 마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();

        System.out.println("MemberService1 = " + memberService1);
        System.out.println("MemberService2 = " + memberService2);

        // memberService1 !== memberService2
        assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singleTonServiceTest() {
        SingleTonService singleTonService1 = SingleTonService.getInstance();
        SingleTonService singleTonService2 = SingleTonService.getInstance();

        System.out.println("singletonService1 = " + singleTonService1);
        System.out.println("singletonService2 = " + singleTonService2);

        assertThat(singleTonService1).isSameAs(singleTonService2);

        // same == 객체 인스턴스 비교
        // equal java equal 메소드
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {
//        AppConfig appConfig = new AppConfig();
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        System.out.println("MemberService1 = " + memberService1);
        System.out.println("MemberService2 = " + memberService2);

        // memberService1 !== memberService2
        assertThat(memberService1).isSameAs(memberService2);
    }
}

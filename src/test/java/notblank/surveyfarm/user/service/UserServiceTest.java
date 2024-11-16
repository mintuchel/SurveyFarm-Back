package notblank.surveyfarm.user.service;

import net.datafaker.Faker;
import notblank.surveyfarm.domain.user.dto.response.UserResponse;
import notblank.surveyfarm.domain.user.entity.User;
import notblank.surveyfarm.domain.user.repository.UserRepository;
import notblank.surveyfarm.domain.user.service.UserService;
import notblank.surveyfarm.domain.utility.CodeConverter;
import notblank.surveyfarm.domain.utility.DTOConverter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Spy
    private CodeConverter codeConverter = new CodeConverter();
    @Spy
    private DTOConverter dtoConverter = new DTOConverter(codeConverter);

    @Mock
    private User user;

    private Faker faker = new Faker();

    @BeforeEach
    public void testSetUp(){
        // spy 객체 초기화
        codeConverter.initCodeConverter();

        when(user.getId()).thenReturn(faker.number().randomDigitNotZero());
        when(user.getPassword()).thenReturn(faker.starCraft().character());
        when(user.getNickName()).thenReturn(faker.name().firstName());
        when(user.getRegionCode()).thenReturn(faker.number().numberBetween(1,100));
        when(user.getJobCode()).thenReturn(faker.number().numberBetween(1,100));
        when(user.getAgeCode()).thenReturn(faker.number().numberBetween(1,100));
        when(user.getGenderCode()).thenReturn(faker.number().numberBetween(1,2));
    }

    @Test
    @DisplayName("유저 조회 성공")
    public void getUserByIdSuccess(){
        // given
        given(userRepository.findById(user.getId())).willReturn(Optional.of(user));

        // when
        UserResponse response = userService.getUserResponseById(user.getId());

        // then
        Assertions.assertThat(response.id()).isEqualTo(user.getId());
        Assertions.assertThat(response.nickName()).isEqualTo(user.getNickName());

        System.out.println(response.id());
        System.out.println(response.nickName());
        System.out.println(response.regionList());
        System.out.println(response.jobList());
        System.out.println(response.ageList());
        System.out.println(response.genderList());
    }
}

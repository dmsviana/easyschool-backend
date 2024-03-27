package br.edu.ifpb.ads.easyschool.producers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.edu.ifpb.ads.easyschool.controllers.dtos.request.EmailDto;
import br.edu.ifpb.ads.easyschool.model.Course;
import br.edu.ifpb.ads.easyschool.model.Student;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CourseProducer {
    
    private final RabbitTemplate rabbitTemplate;

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;


    public void publishCourseEnrollmentEmail(Student student, Course course) {
        EmailDto emailDto = new EmailDto();
        emailDto.setStudentId(student.getId());
        emailDto.setCourseId(course.getId());
        emailDto.setEmailTo(student.getEmail());
        emailDto.setSubject("Inscrição no curso " + course.getName());
        emailDto.setText("Olá, " + student.getName() + "!\n\n" +
                 "Estamos muito felizes em te receber na Easy! Escola de Idiomas.\n\n" +
                 "Você acabou de se inscrever no curso " + course.getName() + ", que é um passo importante para alcançar seus objetivos de aprendizado de idiomas.\n\n" +
                 "O curso ocorre nos dias: " + course.getDaysOfWeek() + ".\n\n" +
                 "Aqui na Easy! temos uma equipe dedicada e qualificada que está ansiosa para te ajudar a dominar o idioma de sua escolha.\n\n" +
                 "Se tiver alguma dúvida ou precisar de mais informações, não hesite em entrar em contato conosco.\n\n" +
                 "Boa sorte no seu curso e até a próxima!\n\n" +
                 "Com os melhores cumprimentos,\n" +
                 "Equipe Easy! Escola de Idiomas");

        rabbitTemplate.convertAndSend("", routingKey, emailDto);
    }

}

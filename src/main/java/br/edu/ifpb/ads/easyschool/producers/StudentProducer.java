package br.edu.ifpb.ads.easyschool.producers;


import br.edu.ifpb.ads.easyschool.dtos.request.EmailDto;
import br.edu.ifpb.ads.easyschool.model.Student;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StudentProducer {

    private final RabbitTemplate rabbitTemplate;

    public StudentProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;

    public void publishMessageEmail(Student student){
        var emailDto = new EmailDto();
        emailDto.setStudentId(student.getId());
        emailDto.setEmailTo(student.getEmail());
        emailDto.setSubject("Agradecemos sua inscrição");
        emailDto.setText("Olá, " + student.getName() + "!\n\n" + "Seja bem-vindo a Easy! Escola de Idiomas.\n\n"
                + "Segue em anexo o contrato de matrícula.\n\n" + "Atenciosamente,\n"
                + "Equipe Easy! Escola de Idiomas");

        rabbitTemplate.convertAndSend("", routingKey, emailDto);
    }


}

package com.librys.bibliotecalibrys.api.exceptionhandler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.librys.bibliotecalibrys.domain.exception.EntidadeEmUsoException;
import com.librys.bibliotecalibrys.domain.exception.EntidadeNaoEncontradaException;
import com.librys.bibliotecalibrys.domain.exception.NegocioException;
import com.librys.bibliotecalibrys.infrastructure.service.email.EmailException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String MSG_ERRO_GENERICA_USUARIO_FINAL
            = "Ocorreu um erro interno inesperado no sistema. " +
            "Tente novamente e se o problema persistir, entre em contato com o administrador do sistema.";

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        if (body == null) {
            body = Problema.builder()
                    .titulo(status.toString())
                    .status(status.value())
                    .build();
        } else if (body instanceof String) {
            body = Problema.builder()
                    .titulo((String) body)
                    .status(status.value())
                    .build();
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);

        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        }

        TipoProblema tipoProblema = TipoProblema.MENSAGEM_INCOMPREENSIVEL;
        String detalhe = "O corpo da requisição está inválido. Verifique erro de sintaxe.";
        Problema problema = createProblemBuilder((HttpStatus) status, tipoProblema, detalhe).build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), headers, (HttpStatus) status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                                   HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        TipoProblema tipoProblema = TipoProblema.RECURSO_NAO_ENCONTRADO;
        String detail = String.format("O recurso %s, que você tentou acessar, é inexistente.",
                ex.getRequestURL());

        Problema problem = createProblemBuilder((HttpStatus)status, tipoProblema, detail).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatusCode status, WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            TipoProblema tipoProblema = TipoProblema.PARAMETRO_INVALIDO;

            String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
                            + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                    ex.getValue(), ex.getValue(), ex.getRequiredType().getSimpleName());

            Problema problem = createProblemBuilder((HttpStatus)status, tipoProblema, detail).build();

            return handleExceptionInternal(ex, problem, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        TipoProblema tipoProblema = TipoProblema.ERRO_DE_SISTEMA;
        String detalhe = MSG_ERRO_GENERICA_USUARIO_FINAL;

        ex.printStackTrace();

        Problema problema = createProblemBuilder(status, tipoProblema, detalhe).build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }
    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        TipoProblema tipoProblema = TipoProblema.ENTIDADE_NAO_ENCONTRADA;
        String detalhe = ex.getMessage();

        Problema problema = createProblemBuilder(status, tipoProblema, detalhe).build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(NegocioException ex, WebRequest request){

        HttpStatus status = HttpStatus.BAD_REQUEST;
        TipoProblema tipoProblema = TipoProblema.ENTIDADE_NAO_ENCONTRADA;
        String detalhe = ex.getMessage();

        Problema problema = createProblemBuilder(status, tipoProblema, detalhe).build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleEntidadeEmUso(EntidadeEmUsoException ex, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        TipoProblema tipoProblema = TipoProblema.ENTIDADE_EM_USO;
        String detalhe = ex.getMessage();

        Problema problema = createProblemBuilder(status, tipoProblema, detalhe).build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EmailException.class)
    public ResponseEntity<?> handleEmailNaoEnviado(EmailException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        TipoProblema tipoProblema = TipoProblema.DADOS_INVALIDOS;
        String detalhe = ex.getMessage();

        Problema problema = createProblemBuilder(status, tipoProblema, detalhe).build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleValidationInternal(Exception ex,
                                                            BindingResult bindingResult, HttpHeaders headers,
                                                            HttpStatus status, WebRequest request) {
        TipoProblema tipoProblema = TipoProblema.DADOS_INVALIDOS;
        String detalhe = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

        List<Problema.Object> problemObjects = bindingResult.getAllErrors().stream().map(objectError -> {
                    String mensagem = getMessageSource().getMessage(objectError, LocaleContextHolder.getLocale());

                    String nome = objectError.getObjectName();

                    if (objectError instanceof FieldError) {
                        nome = ((FieldError) objectError).getField();
                    }

                    return Problema.Object.builder().nome(nome).mensagemUsuario(mensagem).build();
        }).collect(Collectors.toList());

        Problema problema = createProblemBuilder( status, tipoProblema, detalhe).objects(problemObjects).build();
        return handleExceptionInternal(ex, problema, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String path = joinPath(ex.getPath());

        TipoProblema tipoProblema = TipoProblema.MENSAGEM_INCOMPREENSIVEL;
        String detalhe = String.format("A propriedade '%s' não existe. Corrija ou remova essa propriedade e tente novamente.", path);

        Problema problema = createProblemBuilder((HttpStatus)status, tipoProblema, detalhe) .build();


        return handleExceptionInternal(ex, problema, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        String path = joinPath(ex.getPath());

        TipoProblema problemType = TipoProblema.MENSAGEM_INCOMPREENSIVEL;
        String detalhe = String.format("A propriedade '%s' recebeu o valor '%s', que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                path, ex.getValue(), ex.getTargetType().getSimpleName());

        Problema problema = createProblemBuilder((HttpStatus) status, problemType, detalhe) .build();

        return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
    }
    private Problema.ProblemaBuilder createProblemBuilder(HttpStatus status, TipoProblema tipoProblema, String detail) {
        return Problema.builder()
                .status(status.value())
                .titulo(tipoProblema.getTitulo())
                .detalhe(detail);
    }
    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));
    }


}

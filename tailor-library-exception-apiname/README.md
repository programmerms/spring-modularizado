# 🧩 tailor-library-exception

Biblioteca de tratamento global de exceções para aplicações **Spring Boot**.  
Ao incluir esta dependência, sua API já estará automaticamente configurada para capturar e tratar erros comuns de forma padronizada — sem precisar criar manualmente um `@ControllerAdvice` ou duplicar código entre projetos.

---

## 🚀 Recursos

- ✅ Tratamento automático de exceções globais (HTTP 400, 404, 422, 500)
- 🧠 Detecção automática do nome da API (por `app.name`, `ApplicationContext`, ou `MANIFEST.MF`)
- ⚙️ AutoConfiguração Spring Boot (`ExceptionAutoConfiguration`)
- 🪵 Log limpo via **SLF4J**
- 🔁 Possibilidade de sobrescrever o handler na aplicação consumidora
- 🔍 Suporte a validações do Bean Validation (`@Valid`, `@NotNull`, etc.)

---

## 📦 Instalação

### Maven

Adicione a dependência no `pom.xml` do seu projeto:

```xml
<dependency>
    <groupId>br.com.itau.tailor.library</groupId>
    <artifactId>tailor-library-exception</artifactId>
    <version>0.0.1</version>
</dependency>
```

### Gradle

```groovy
implementation 'br.com.itau.tailor.library:tailor-library-exception:0.0.1'
```

> ⚠️ Certifique-se de que o repositório Artifactory esteja configurado corretamente no seu `settings.xml` (Maven) ou `build.gradle` (Gradle).

---

## 🧩 Configuração opcional

A lib tenta descobrir o nome da sua API automaticamente, mas você pode definir manualmente no `application.yml`:

```yaml
app:
  name: tailor-api-clientes
```

Isso ajuda a identificar qual API gerou a exceção nos logs.

---

## ⚙️ Como funciona

Basta incluir a lib no seu classpath.  
O Spring Boot detecta automaticamente a configuração (`ExceptionAutoConfiguration`) e ativa o `GlobalExceptionHandler`.

### Estrutura de resposta de erro (JSON)
```json
{
  "code": "VALIDATION_ERROR",
  "message": "Field validation error",
  "details": {
    "nome": "não pode ser vazio",
    "idade": "deve ser maior que 18"
  }
}
```

### Tipos de erros tratados
| Tipo de erro | Código HTTP | Code | Descrição |
|---------------|-------------|------|------------|
| Validação de campos (`@Valid`) | 400 | `VALIDATION_ERROR` | Erros de validação Bean Validation |
| Recurso não encontrado (`NotFoundException`) | 404 | `NOT_FOUND` | Entidade não encontrada |
| Erro de negócio (`BusinessException`) | 422 | `BUSINESS_ERROR` | Regras de domínio violadas |
| Exceções genéricas | 500 | `INTERNAL_ERROR` | Erros não tratados |

---

## 🧱 Customização

Se quiser tratar exceções específicas da sua aplicação, basta **extender** o handler da lib:

```java
@ControllerAdvice
public class CustomExceptionHandler extends GlobalExceptionHandler {
    
    public CustomExceptionHandler(ApiNameResolver apiNameResolver) {
        super(apiNameResolver);
    }

    @ExceptionHandler(MyDomainException.class)
    public ResponseEntity<ErrorResponse> handleMyDomain(MyDomainException ex) {
        log.warn("Erro de domínio na API [{}]: {}", apiNameResolver.resolve(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ErrorResponse("MY_DOMAIN_ERROR", ex.getMessage(), null));
    }
}
```

---

## 🔧 Logging

Os logs seguem o formato simplificado:

```
Unexpected error in API [tailor-api-clientes]: Falha ao conectar no serviço externo
```

> Dica: se quiser incluir stacktrace em ambientes `dev`, você pode criar uma propriedade opcional:
> ```yaml
> app:
>   exception:
>     debug: true
> ```
> (suporte futuro planejado 😉)

---

## 🧰 Pacotes principais

| Pacote | Descrição |
|--------|------------|
| `br.com.itau.tailor.exception.handler` | Contém o `GlobalExceptionHandler` |
| `br.com.itau.tailor.exception.util` | Contém o `ApiNameResolver` |
| `br.com.itau.tailor.exception.config` | AutoConfiguração da lib |
| `br.com.itau.tailor.exception.model` | Estrutura de resposta `ErrorResponse` |
| `br.com.itau.tailor.exception.exception` | Exceções customizadas (`BusinessException`, `NotFoundException`) |

---

## 🧪 Teste rápido

```bash
mvn clean install
```

Depois importe no seu projeto e levante a API.  
Qualquer exceção lançada será capturada e retornada de forma estruturada.

---

## 🤝 Contribuição

Pull Requests são bem-vindos!  
Siga o padrão de formatação, inclua testes unitários e mantenha os pacotes organizados.

---

## 📜 Licença

Uso interno — Itaú Unibanco (Tailor Platform)

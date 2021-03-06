package infnet.assessement.demo.controller;

import infnet.assessement.demo.repository.Autor;
import infnet.assessement.demo.repository.Usuario;
import infnet.assessement.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("usuario")
@Controller
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    Usuario usuario;
    Autor autor;

    @GetMapping(value = "criacao")
    public String createPage(Map<String, Object> model) {
        model.put("message",null);
        model.put("success",false);
        return "secure/client/create";
    }

    @PostMapping(value = "criacao")
    public void save(@RequestParam("nome") String nome,
                     @RequestParam("sobrenome") String sobrenome,
                     @RequestParam("idade") String idade,
                     @RequestParam("rua") String rua,
                     @RequestParam("numero") String numero,
                     @RequestParam("complemento") String complemento,
                     @RequestParam("bairro") String bairro,
                     @RequestParam("cidade") String cidade,
                     @RequestParam("cep") String cep,
                     @RequestParam("email") String email,
                     @RequestParam("senha") String senha,
                     Map<String,Object> model) {
        //password = passwordCript(password);
        if(usuarioRepository.findByEmail(email)!= null) {
            model.put("usuario",usuario);
            model.put("message", "Email ja existe");
            model.put("success",false);
            return;
        }
        if(StringUtils.hasText(nome) && StringUtils.hasText(sobrenome) &&
                StringUtils.hasText(idade)&& StringUtils.hasText(rua) &&
                StringUtils.hasText(numero) && StringUtils.hasText(complemento) &&  StringUtils.hasText(bairro)&&
                StringUtils.hasText(cidade) &&  StringUtils.hasText(cep) &&  StringUtils.hasText(email) &&  StringUtils.hasText(senha)) {
            Usuario usuario = new Usuario(nome,sobrenome,idade,rua,numero,complemento,cep,bairro,cidade,email,senha);
            usuarioRepository.save(usuario);
            model.put("message", "Your account has been created");
            model.put("success", true);
        } else {
            model.put("message", "Ops! Please, fill all inputs");
            model.put("success", false);
        }
    }

    @GetMapping
    public Iterable<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    @PostMapping(value="update")
    public void update(@RequestParam("nome") String nome,
                       @RequestParam("sobrenome") String sobrenome,
                       @RequestParam("idade") String idade,
                       @RequestParam("rua") String rua,
                       @RequestParam("numero") String numero,
                       @RequestParam("complemento") String complemento,
                       @RequestParam("bairro") String bairro,
                       @RequestParam("cidade") String cidade,
                       @RequestParam("genero") String cep,
                       @RequestParam("email") String email,
                       @RequestParam("senha") String senha,
                       @RequestParam("id") Long id,
                       Map<String,Object> model) {
        if(usuarioRepository.findByEmail(email) == null) {
            model.put("usuario",usuario);
            model.put("message", "Email not exist");
            model.put("success",false);
            return;
        }
        //password = passwordCript(password);
        Usuario usuario = usuarioRepository.findOne(id);
        usuario.setNome(nome);
        usuario.setSobrenome(sobrenome);
        usuario.setIdade(idade);
        usuario.setRua(rua);
        usuario.setNumero(numero);
        usuario.setComplemento(complemento);
        usuario.setBairro(bairro);
        usuario.setCidade(cidade);
        usuario.setCep(cep);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuarioRepository.save(usuario);
    }


}

package com.zoonotrack.config;

import com.zoonotrack.model.*;
import com.zoonotrack.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Carrega massa robusta para permitir testes completos e geração dos relatórios.
 * Executa apenas quando a base de dados está vazia.
 */
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private MoradorRepository moradorRepository;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private AgenteSaudeRepository agenteSaudeRepository;

    @Autowired
    private InspecaoRepository inspecaoRepository;

    @Override
    public void run(String... args) {
        if (moradorRepository.count() > 0
                || animalRepository.count() > 0
                || agenteSaudeRepository.count() > 0
                || inspecaoRepository.count() > 0) {
            return;
        }

        List<Morador> moradores = cadastrarMoradoresEAnimais();
        List<AgenteSaude> agentes = cadastrarAgentes();
        criarInspecoesEmLote(moradores, agentes);
    }

    private List<Morador> cadastrarMoradoresEAnimais() {
        List<Morador> moradores = new ArrayList<>();

        Morador maria = salvarMorador("Maria Aparecida", "Rua das Larvas", "102", "Jardim Azul",
                "38400-000", "(34) 98822-1111", "Histórico de infestação por roedores em 2023");
        Morador joao = salvarMorador("João Ferreira", "Av. Cascavel", "250", "Parque Verde",
                "38402-345", "(34) 98822-3331", "Possui criatório informal de aves.");
        Morador luciana = salvarMorador("Luciana Ramos", "Rua das Bromélias", "15", "Vila Serra",
                "38405-210", "(34) 99644-2200", "Residência com quintal amplo e compostagem.");

        moradores.add(maria);
        moradores.add(joao);
        moradores.add(luciana);

        salvarAnimal("Thor", EspecieAnimal.CANINO, maria, LocalDate.of(2020, 5, 12), true, true, true,
                "Cão dócil, convive com crianças.");
        salvarAnimal("Nina", EspecieAnimal.FELINO, maria, LocalDate.of(2022, 2, 4), false, false, false,
                "Filhote resgatado recentemente.");
        salvarAnimal("Apolo", EspecieAnimal.CANINO, joao, LocalDate.of(2018, 9, 30), true, true, false,
                "Cão de guarda, permanece externo.");
        salvarAnimal("Coral", EspecieAnimal.AVE, joao, LocalDate.of(2023, 1, 15), true, false, false,
                "Galo criado solto no quintal.");
        salvarAnimal("Mel", EspecieAnimal.ROEDOR, luciana, LocalDate.of(2021, 11, 5), true, true, false,
                "Hamster de estimação em gaiola apropriada.");

        String[] bairros = {"Santa Mônica", "Luizote de Freitas", "Umuarama", "Patrimônio", "Nossa Senhora Aparecida"};
        String[] observacoes = {
                "Relata acúmulo de água após chuvas fortes.",
                "Cães circulam livremente pelo quintal.",
                "Vizinha informou presença de morcegos no sótão.",
                "Mantém horta orgânica e compostagem.",
                "Denúncia por resíduos sem acondicionamento."
        };

        for (int i = 1; i <= 100; i++) {
            Morador morador = salvarMorador(
                    "Morador " + i,
                    "Rua das Sentinelas " + i,
                    String.valueOf(100 + i),
                    bairros[i % bairros.length],
                    String.format("384%02d-%03d", i % 80, 100 + i),
                    String.format("(34) 9%04d-%04d", 7000 + i, 3000 + i),
                    observacoes[i % observacoes.length]
            );
            moradores.add(morador);

            if (i % 3 != 0) {
                salvarAnimal("Sentinela " + i, EspecieAnimal.CANINO, morador,
                        LocalDate.now().minusYears(1 + (i % 7)).minusMonths(i % 12),
                        i % 2 == 0, i % 4 == 0, i % 5 == 0,
                        "Cão utilizado como alerta para presença de roedores.");
            }
            if (i % 5 == 0) {
                salvarAnimal("Felix " + i, EspecieAnimal.FELINO, morador,
                        LocalDate.now().minusYears(2 + (i % 3)),
                        true, false, false,
                        "Gato responsável por controle de pequenos roedores.");
            }
        }

        return moradores;
    }

    private List<AgenteSaude> cadastrarAgentes() {
        List<AgenteSaude> agentes = new ArrayList<>();

        agentes.add(salvarAgente("Alex Souza", "AG001", "Equipe Leste", "(34) 98777-1111", "alex@zoonotrack.gov.br"));
        agentes.add(salvarAgente("Bianca Lima", "AG002", "Equipe Norte", "(34) 98777-2222", "bianca@zoonotrack.gov.br"));
        agentes.add(salvarAgente("Carlos Prado", "AG003", "Equipe Sul", "(34) 98777-3333", "carlos@zoonotrack.gov.br"));

        String[] primeirosNomes = {"Helena", "Otávio", "Sofia", "Miguel", "Isabela", "Rafael", "Lara", "Paulo", "Fernanda", "Caio"};
        String[] sobrenomes = {"Dias", "Barbosa", "Silva", "Gomes", "Rezende", "Almeida", "Campos", "Pereira", "Martins", "Costa"};
        String[] equipes = {"Equipe Norte", "Equipe Sul", "Equipe Leste", "Equipe Oeste"};

        int sequencial = 4;
        for (int i = 0; i < 17; i++) {
            String nome = primeirosNomes[i % primeirosNomes.length] + " " + sobrenomes[(i + 3) % sobrenomes.length];
            agentes.add(salvarAgente(
                    nome,
                    String.format("AG%03d", sequencial++),
                    equipes[i % equipes.length],
                    String.format("(34) 98%03d-%04d", 500 + i, 2000 + i),
                    nome.toLowerCase().replace(" ", ".") + "@zoonotrack.gov.br"
            ));
        }

        return agentes;
    }

    private void criarInspecoesEmLote(List<Morador> moradores, List<AgenteSaude> agentes) {
        Random random = new Random(2024);
        LocalDate hoje = LocalDate.now();

        for (int i = 0; i < 150; i++) {
            Morador morador = moradores.get(random.nextInt(moradores.size()));
            AgenteSaude agente = agentes.get(random.nextInt(agentes.size()));

            LocalDate data = hoje.minusDays(random.nextInt(120));
            StatusInspecao status = switch (i % 4) {
                case 0 -> StatusInspecao.CONCLUIDA;
                case 1 -> StatusInspecao.EM_ANDAMENTO;
                case 2 -> StatusInspecao.AGENDADA;
                default -> StatusInspecao.CONCLUIDA;
            };

            List<InspecaoItem> itens = new ArrayList<>();
            itens.add(item(TipoFoco.RESIDUOS_ORGANICOS,
                    "Resíduos orgânicos espalhados no quintal atraindo vetores.",
                    4 + random.nextInt(4),
                    "Acondicionar resíduos e manter tambores fechados.",
                    null));

            if (i % 3 == 0) {
                itens.add(item(TipoFoco.AGUA_PARADA,
                        "Larvas de mosquito da dengue em recipientes com água parada.",
                        7 + random.nextInt(3),
                        "Aplicado larvicida e orientado eliminar possíveis criadouros.",
                        null));
            }

            if (i % 5 == 0) {
                itens.add(item(TipoFoco.AGUA_PARADA,
                        "Presença de Aedes aegypti com suspeita de transmissão de zika.",
                        8 + random.nextInt(2),
                        "Equipe realizou bloqueio e orientação sobre sintomas.",
                        null));
            }

            if (i % 7 == 0) {
                itens.add(item(TipoFoco.OUTROS,
                        "Colônia de morcegos identificada no forro da residência.",
                        6,
                        "Acionado serviço especializado para remoção e isolamento.",
                        null));
            }

            List<Animal> animaisDoMorador = animalRepository.findByMorador(morador);
            if (!animaisDoMorador.isEmpty() && i % 2 == 0) {
                Animal animal = animaisDoMorador.get(random.nextInt(animaisDoMorador.size()));
                itens.add(item(TipoFoco.CARNIVOROS_DOMESTICOS,
                        "Vários carrapatos nos cães e no local onde permanecem.",
                        6 + random.nextInt(3),
                        "Orientada a aplicação de acaricida e higienização do canil.",
                        animal));
            }

            if (i % 9 == 0) {
                itens.add(item(TipoFoco.OUTROS,
                        "Foco com larvas suspeitas de vírus chikungunya próximo a bromélias.",
                        7,
                        "Retirada de bromélias e monitoramento por 15 dias.",
                        null));
            }

            salvarInspecao(data, status, morador, agente,
                    "Visita " + (i + 1) + " realizada pela " + agente.getEquipe() + ".",
                    itens);
        }
    }

    private Morador salvarMorador(String nome, String rua, String numero, String bairro, String cep,
                                  String telefone, String observacao) {
        Morador morador = new Morador();
        morador.setNome(nome);
        morador.setRua(rua);
        morador.setNumero(numero);
        morador.setBairro(bairro);
        morador.setCep(cep);
        morador.setTelefone(telefone);
        morador.setObservacaoRiscoHistorico(observacao);
        return moradorRepository.save(morador);
    }

    private Animal salvarAnimal(String nome, EspecieAnimal especie, Morador morador, LocalDate nascimento,
                                boolean vacinado, boolean castrado, boolean microchipado, String observacoes) {
        Animal animal = new Animal();
        animal.setNome(nome);
        animal.setEspecie(especie);
        animal.setMorador(morador);
        animal.setDataNascimento(nascimento);
        animal.setVacinado(vacinado);
        animal.setCastrado(castrado);
        animal.setMicrochipado(microchipado);
        animal.setObservacoes(observacoes);
        return animalRepository.save(animal);
    }

    private AgenteSaude salvarAgente(String nome, String matricula, String equipe, String telefone, String email) {
        AgenteSaude agente = new AgenteSaude();
        agente.setNome(nome);
        agente.setMatricula(matricula);
        agente.setEquipe(equipe);
        agente.setTelefone(telefone);
        agente.setEmail(email);
        agente.setAtivo(true);
        return agenteSaudeRepository.save(agente);
    }

    private void salvarInspecao(LocalDate data, StatusInspecao status, Morador morador, AgenteSaude agente,
                                String observacoes, List<InspecaoItem> itens) {
        Inspecao inspecao = new Inspecao();
        inspecao.setData(data);
        inspecao.setStatus(status);
        inspecao.setMorador(morador);
        inspecao.setAgente(agente);
        inspecao.setObservacoesGerais(observacoes);
        itens.forEach(item -> {
            item.setInspecao(inspecao);
            inspecao.getItens().add(item);
        });
        inspecaoRepository.save(inspecao);
    }

    private InspecaoItem item(TipoFoco tipo, String descricao, int risco, String recomendacao, Animal animal) {
        InspecaoItem item = new InspecaoItem();
        item.setTipoFoco(tipo);
        item.setDescricao(descricao);
        item.setNivelRisco(risco);
        item.setRecomendacao(recomendacao);
        item.setAnimal(animal);
        return item;
    }
}

# Jogo Freeway - Sistemas Operacionais

Este projeto consiste na implementação do jogo **Freeway**, inspirado no clássico "Game One: Lake Shore Drive", utilizando a linguagem Java. O jogo foi desenvolvido como parte da disciplina de **Sistemas Operacionais** no curso de Engenharia de Computação da **Universidade Federal de Itajubá (UNIFEI)**.

![FreeWaySO](https://github.com/user-attachments/assets/912dcc5f-184f-4264-b4bd-26f2e1bf7d97)


## 📝 Descrição

O objetivo do jogo é guiar as galinhas para atravessarem uma avenida movimentada, evitando colisões com veículos em movimento. O projeto faz uso de conceitos fundamentais de Sistemas Operacionais, como threads e semáforos, para gerenciar a sincronização das ações dos jogadores e veículos de forma eficiente e segura.

### Funcionalidades

- Controle de galinhas por diferentes jogadores (cada jogador em uma thread).
- Veículos se movimentando em diferentes velocidades e direções.
- Implementação de exclusão mútua com semáforos para evitar problemas de concorrência.
- Atualização contínua do estado do jogo em uma interface gráfica com Java Swing.

## 🛠️ Tecnologias Utilizadas

- **Java**: Linguagem de programação principal.
- **Java Swing**: Para criação da interface gráfica do jogo.
- **Threads e Semáforos**: Para controle de concorrência.
- **Estruturas de Dados**: Matrizes para representar o mapa do jogo e as posições de jogadores e veículos.

## 🎯 Objetivos do Projeto

1. Implementar threads para os jogadores (controle das galinhas).
2. Implementar threads para os veículos em movimento.
3. Utilizar semáforos para sincronizar o acesso às posições no mapa.
4. Criar uma interface gráfica para o jogo utilizando Java Swing.
5. Garantir a jogabilidade fluida e segura através de conceitos de sincronismo.

## 🚀 Execução do Projeto

### Requisitos

- **Java 8** ou superior instalado.
- IDE de sua preferência para compilar e executar o código (ex.: IntelliJ, Eclipse, VS Code).

### Passos

1. Clone este repositório:

   ```bash
   git clone https://github.com/seu-usuario/jogo-freeway.git
   ```

2. Abra o projeto na sua IDE de escolha.

3. Compile e execute o arquivo main.java

## 🖼️ Estrutura do Jogo

### Mapa do Jogo
- Representado por uma matriz carregada a partir do arquivo MeuMapa.txt.
- Diferentes elementos do jogo (ruas, calçadas, paredes) são definidos por números na matriz.
### Carros
- Criados pela classe Carro.java.
- Velocidade e direção definidas dinamicamente.
### Jogadores
- Controlados pelas classes Player.java e KeyHandler.java.
- Cada jogador utiliza uma thread para ações independentes.
### Sincronização
- A sincronização é feita por meio de semáforos, garantindo acesso seguro às regiões críticas do código.
### 📄 Conclusão
O projeto demonstra a aplicação prática de conceitos de Sistemas Operacionais no desenvolvimento de um jogo funcional. A utilização de threads e semáforos permite explorar concorrência, exclusão mútua e sincronismo, garantindo uma experiência fluida e responsiva para os usuários.

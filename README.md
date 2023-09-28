# Documentação do Sistema de Excursões

## Visão Geral

O Sistema de Excursões é uma aplicação que permite gerenciar excursões, reservas e calcular o valor total de uma excursão. Ele oferece uma interface gráfica (Swing) para interação com o usuário.

## Funcionalidades

### 1. Criar Excursão
- Permite ao usuário criar uma nova excursão fornecendo código, preço e número máximo de reservas.

### 2. Recuperar Excursão Existente
- Permite ao usuário recuperar uma excursão existente informando seu código.

### 3. Criar Reserva
- Permite ao usuário criar uma reserva fornecendo CPF e nome. Evita reservas duplicadas pelo mesmo nome.

### 4. Cancelar Reserva Individual
- Permite ao usuário cancelar uma reserva fornecendo CPF e nome.

### 5. Cancelar Reserva em Grupo
- Permite ao usuário cancelar todas as reservas de um CPF.

### 6. Listar Reservas por CPF
- Permite ao usuário listar as reservas fornecendo os dígitos do CPF.

### 7. Listar Reservas por Nome
- Permite ao usuário listar as reservas fornecendo um texto que compõe o nome.

### 8. Calcular Valor Total
- Calcula o valor total da excursão considerando as reservas realizadas.

### 9. Salvar em Arquivo
- Salva os dados da excursão em um arquivo para recuperação posterior.

### 10. Carregar de Arquivo
- Carrega os dados da excursão previamente salvos.

## Implementações Realizadas

A aplicação foi implementada em Java, utilizando a biblioteca Swing para a interface gráfica. Abaixo estão as principais implementações realizadas:

### - Criação da Interface Gráfica (Swing)
- Desenvolvimento da interface gráfica para interação com o usuário.

### - Implementação das Funcionalidades
- Implementação das funcionalidades de criar excursão, recuperar excursão, criar reserva, cancelar reserva individual e em grupo, listar reservas por CPF e por nome, calcular valor total, salvar em arquivo e carregar de arquivo.

### - Tratamento de Exceções
- Adição de tratamento de exceções para situações como número máximo de reservas atingido e reservas duplicadas.

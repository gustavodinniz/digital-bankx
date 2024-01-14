**Processamento de Transações em Lote no DigitalBankX com Upload para S3 e SQS Integration**

**Descrição do Problema:** O DigitalBankX agora recebe transações em um arquivo CSV que é colocado em um bucket S3. Para
otimizar o processamento dessas transações, implementaremos um sistema de processamento em lote usando o Spring Batch. A
integração será feita através do Amazon Simple Queue Service (SQS), que será acionado por eventos no S3.

**Objetivo:** Implementar um sistema eficiente de processamento em lote para lidar com as transações financeiras diárias
do DigitalBankX, utilizando o S3 para o upload dos arquivos CSV e o SQS para acionar o processamento em lote.

**Passos do Processo:**

1. **Envio de Transações via CSV para o S3:**

    - Os arquivos CSV contendo as transações serão enviados para um bucket S3 dedicado ao DigitalBankX.
2. **Evento de S3 para SQS:**

    - Um evento é configurado no S3 para acionar automaticamente uma mensagem no SQS sempre que um novo arquivo CSV for
      colocado no bucket.
    - A mensagem no SQS conterá informações como o nome do bucket e a chave (key) do arquivo.
3. **Listener do SQS:**

    - Um listener será configurado para escutar a fila SQS e processar as mensagens recebidas.
    - Ao receber uma mensagem, o sistema extrairá as informações necessárias, incluindo o nome do bucket e a chave do
      arquivo.
4. **Download do Arquivo do S3:**

    - O sistema iniciará o download do arquivo CSV do S3 usando as informações do bucket e da chave recebidas.
    - O arquivo será temporariamente armazenado localmente para o processamento em lote.
5. **Configuração do Job Spring Batch:**

    - Um job Spring Batch será configurado para processar as transações em lote.
    - A etapa de leitura do job será adaptada para ler o arquivo CSV localmente armazenado.
6. **Processamento das Transações:**

    - A etapa de processamento aplicará as regras de negócios necessárias para cada tipo de transação, utilizando os
      dados do arquivo CSV.
7. **Atualização do Status e Relatórios:**

    - Após o processamento bem-sucedido, as transações serão marcadas como concluídas no banco de dados.
    - Os resultados do processamento, incluindo relatórios e estatísticas, podem ser gerados conforme necessário.
8. **Limpeza e Armazenamento do Arquivo:**

    - Após o processamento em lote, o arquivo CSV local será removido ou movido para armazenamento de backup.

**Benefícios Esperados:**

- Melhoria significativa no desempenho do sistema, aproveitando o processamento em lote para grandes volumes de
  transações.
- Facilidade na identificação e correção de transações com falha.
- Integração eficiente entre o S3 e o SQS, proporcionando uma arquitetura mais escalável e assíncrona.

**Tecnologias Utilizadas:**

- Spring Batch para o processamento em lote.
- Amazon S3 para armazenamento de arquivos CSV.
- Amazon SQS para fila de mensagens.
- Banco de dados relacional para armazenar transações e resultados.
- Ferramentas de monitoramento e logging para análise de desempenho.

---

**Como rodar o projeto**

1) Faça o clone do projeto.

2) No diretório: `digital-bankx/src/main/resources/docker-compose` rode o comando:
   `docker-compose up`

3) No diretório: `digital-bankx/src/main/resources/docker-compose/localstack-script/bucket` há um exemplo de arquivo a
   ser
   consumido, basta colocar o arquivo no bucket com o
   comando: `aws s3 cp 20240113-bankx-transactions.csv s3://bankx-transactions --endpoint-url http://localhost:4566
   `

4) Ao colocar o arquivo no bucket, o mesmo será consumido e processado pelo Spring Batch.

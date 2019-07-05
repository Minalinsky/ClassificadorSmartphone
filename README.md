## Classificador de anúncios de e-commerce
##### Alyson Matheus Maruyama Nascimento
Classificador de títulos de ofertas de e-commerces para diferenciar entre **smartphone / não-smartphone** para o conjunto de títulos de anúncios encontrado no arquivo `data_estag_ds.tsv` em /files
 - Linguagem: JAVA
 - Foi utilizada a FonoAPI para recuperar informações e nomes de diversos smartphones do mercado. A API consiste em um banco de dados no qual é possível fazer consultas à modelos de smartphones dado o nome do modelo ou da marca fabricante. Link da API: https://fonoapi.freshpixl.com/
 - Todos os arquivos estão em formato `.tsv` e encontram-se na pasta /files do projeto
 
 #### Abordagem escolhida
 - São classificados como **smartphone** todos os anúncios que vendem produtos **relacionados** à smartphones (por exemplo, acessórios ou capas), e não apenas os anúncios que vendem o celular em si.

#### Arquivos
 - `data_estag_ds.tsv` arquivo de entrada contendo os títulos dos anúncios
 - `phonesdb.tsv` dados recuperados através da API com nomes de fabricantes e modelos de celulares 
 - `output.tsv` arquivo final com os dados filtrados

### Classes
##### Classificador.java
 - Executa o código (main) e acessa a FonoAPI para criar o arquivo phonesdb.tsv com a lista de modelos de telefones.
 - O método createPhonesDB() foi o utilizado para a requisição dos dados da FonoAPI;
 
##### AdTitleComparator.java
 - Possui as funções de comparação e manipulação de dados de entrada e saída.
 
##### FileManager.java
 - Possui as funções para escrita e leitura dos arquivos em disco.
 
### Fluxo do Programa
1. São lidos os dados de entrada de `data_estag_ds.tsv`
2. São lidos os dados de `phonesdb.tsv` que serão utilizados para auxiliar na classificação (caso o arquivo `phonesdb.tsv` não exista no diretório, o programa requisita os dados através da FonoAPI e cria o arquivo)
3. Os dados de `phonesdb.tsv` são tratados para remover os nomes das empresas e deixar somente o nome de modelos de smartphones (sem itens duplicados)
4. Com os modelos de smartphones em mãos, utilizamos eles e os nomes das empresas (no arquivo `brands.tsv`) para filtrar anúncios que citam esses modelos ou marcas
5. Além disso, separam-se os ids dos anúncios selecionados como sendo "smartphone"(utilizando a variável `filteredList`, que possui os anúncios que são relacionados a smartphones) para depois comparar com os ids de todos os anúncios (arquivo de entrada)
6. Comparamos os IDs dos títulos do arquivo bruto (`data_estag_ds.tsv`) com os IDs da lista que foi filtrada. Os anúncios cujos IDs foram selecionados recebem "SMARTPHONE", e os demais, "NÃO-SMARTPHONE"
7. O resultado final é salvo em `output.tsv`
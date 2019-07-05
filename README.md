## Classificador de anúncios de e-commerce
##### Alyson Matheus Maruyama Nascimento
Classificador de títulos de ofertas de e-commerces para diferenciar entre **smartphone / não-smartphone** para o conjunto de títulos de anúncios encontrados no arquivo `data_estag_ds.tsv` em /files
 - Linguagem: JAVA
 - Foi utilizada a FonoAPI para recuperar informações e nomes de diversos smartphones do mercado. A API consiste em um banco de dados no qual é possível fazer consultas à modelos de smartphones dado o nome do modelo ou da marca fabricante. Link da API: https://fonoapi.freshpixl.com/
 - Todos os arquivos estão em formato .tsv e encontram-se na pasta /files do projeto
 - Classificamos como **smartphone** todos os anúncios que vendem produtos relacionados à smartphones (por exemplo, vendendo acessórios), e não apenas os anúncios que vendem o celular

#### Arquivos
 - `data_estag_ds.tsv` arquivo de entrada contendo os títulos dos anúncios
 - `output.tsv` arquivo final com os dados filtrados
 - `phonesdb.tsv` dados recuperados através da API com nomes de fabricantes e modelos de celulares 





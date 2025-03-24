```toml
name = 'create'
method = 'POST'
url = 'http://localhost:8080/product/21'
sortWeight = 4000000
id = '59606f6e-a19c-4469-b174-6003ac7dd96d'

[body]
type = 'JSON'
raw = '''
{
  "id": 21,
  "nom": "Chrome Book Magic Mike",
  "codeArticle": "opg",
  "description": "Ordinateur portable",
  "prix": 1299.99,
  "etat": {
    "id": 1,
  },
  "etiquettes": {
    "id": 3
  }

}'''
```

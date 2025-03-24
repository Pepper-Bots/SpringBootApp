```toml
name = 'update'
method = 'PUT'
url = 'http://localhost:8080/product/1'
sortWeight = 6000000
id = 'd91c0ac1-401a-4d00-8578-a60ad51f7883'

[body]
type = 'JSON'
raw = '''
{
  "id": 1,
  "nom": "Asus Zenbook A14",
  "codeArticle": "aza14",
  "description": "Ordinateur portable léger avec processeur Snapdragon X Elite",
  "prix": 1299.99,
  "etat": {
    "id": 1,
    "nom": "neuf"
  },
  "etiquettes": [
    {
      "id": 3,
      "nom": ""
    }
   
  ]
}'''
```

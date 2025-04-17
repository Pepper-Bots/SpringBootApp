```toml
name = 'create'
method = 'POST'
url = 'http://localhost:8080/product'
sortWeight = 4000000
id = '59606f6e-a19c-4469-b174-6003ac7dd96d'

[auth.bearer]
token = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjQGMuY29tIn0.9ovk0CnolT-RzdXC-HOWMcRz-x1BDJZXdu-YnlQAmLo'

[body]
type = 'JSON'
raw = '''
{
  "nom" : "eeee",
  "description" : "PPPPPPP",
  "prix" : 10,
  "codeArticle" : "125i",
  "etat" : {
    "id" : 2
  },
  "etiquettes" : [
    {
      "id" : 1
    },
    {
      "id" : 4
    }
  ]
}'''
```

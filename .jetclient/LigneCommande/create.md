```toml
name = 'create'
method = 'POST'
url = 'http://localhost:8080/ligneCommande'
sortWeight = 1000000
id = '074fafed-3c0e-4b96-96e7-7c0780fe1372'

[body]
type = 'JSON'
raw = '''
{
  "prixVente" : 1500, 
  "quantite" : 3,
  "produite" : {
    id : 2,
  },
  "commande" : 12
}'''
```

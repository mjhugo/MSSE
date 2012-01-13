@Grab('org.pegdown:pegdown:1.1.0')

import org.pegdown.*

PegDownProcessor p = new PegDownProcessor()

File output = new File('orm.html')
output.text = p.markdownToHtml(new File('orm.md').text)

return
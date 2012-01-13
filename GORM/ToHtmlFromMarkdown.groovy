@Grab('org.pegdown:pegdown:1.1.0')

import org.pegdown.*

PegDownProcessor p = new PegDownProcessor()

File output = new File('/Volumes/SSD2/projects/piragua/msse/2012/GORM/orm.html')
output.text = p.markdownToHtml(new File('/Volumes/SSD2/projects/piragua/msse/2012/GORM/orm.md').text)

return
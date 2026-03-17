#Evangelia Iatridou AM 4676, Spyridoula Tsafoni AM 5373, Aikaterini Panagiota Katsanou AM 5249
from matplotlib import pyplot as plt 

M = [4,6,8,10,12]
E = [383.5725707344537,259.1976069885377,186.0598229953792,172.8766507826408,160.56959154568543]

plt.plot(M,E,marker='*')
plt.xlabel('Number of clusters')
plt.ylabel('Minimum error')
plt.show()

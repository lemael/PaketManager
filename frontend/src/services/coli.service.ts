import apiClient from '@/api/axios.instance';

export interface Coli {
  id?: number;
  coliNumber?: string;
  recipient: string;
  formatAndWeight: string;
  status: 'AUSSTEHEND' | 'IN_ARBEIT' | 'GELIEFERT' | 'ANOMALIE';
  kundeId?: number;
  kundeName?: string;
  transportId?: number;
  transportZone?: string;
}

export const coliService = {
  async getAll(): Promise<Coli[]> {
    const response = await apiClient.get<Coli[]>('/colis');
    return response.data;
  },
  async getById(id: number): Promise<Coli> {
    const response = await apiClient.get<Coli>(`/colis/${id}`);
    return response.data;
  },
  async create(coli: Coli): Promise<Coli> {
    const response = await apiClient.post<Coli>('/colis', coli);
    return response.data;
  },

  async update(id: number, coli: Partial<Coli>): Promise<Coli> {
    const response = await apiClient.put<Coli>(`/colis/${id}`, coli);
    return response.data;
  },

  async delete(id: number): Promise<void> {
    await apiClient.delete(`/colis/${id}`);
  },
};